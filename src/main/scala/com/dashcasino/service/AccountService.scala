package com.dashcasino.service

import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.dao.sql.{TransactionSqlDao, AccountSqlDao}
import com.dashcasino.model.{Account, Transaction, User}

/**
  * Created by freezing on 1/30/16.
  */
class AccountService(implicit val accountDao: AccountSqlDao, transactionDao: TransactionSqlDao,
                     walletService: WalletService, commandService: CommandService) {
  // TODO: Refactor the whole class to use getAccount as partial function (figure out how to do that) and get rid of redundant code
  val accountNotFound = ResultCode(DaoStatusCode.SERVER_ERROR, "Account not found")

  val NOT_CONFIRMED = 0
  val CONFIRMED = 1

  class NotEnoughMoneyException(message: String) extends Exception(message)

  // TODO: Maybe use amount - EPS?
  private def checkMoney(account: Account, amount: BigDecimal) = {
    if (account.amount > amount) throw new NotEnoughMoneyException(s"Not enough money: ${account.amount}")
  }

  def internalUpdate(account: Account, gameId: Int, commandId: Int, amount: BigDecimal) = {
    // TODO: Use ReasonFactory to create Reason
    val reason = "{}"
    val newTransaction = Transaction(-1, account.id, -amount, commandId, reason, NOT_CONFIRMED, -1)
    transactionDao.insertTransaction(newTransaction)

    // Update the account
    accountDao.updateMoney(account.id, -amount)

    val confirmedTransaction = Transaction(-1, account.id, -amount, commandId, reason, CONFIRMED, -1)
    transactionDao.insertTransaction(confirmedTransaction)
  }

  /**
    * This is not thread safe on account level (can be done by using SQL transactions but if changed to NoSQL later it would make no sense.
    * Thread-safe should be handled in the top level Actor that will process all messages per Account (which is good solution).
    *
    * @param user
    * @param gameId
    * @param commandId This is needed to create a reason since there can be many internal withdrawals (BET, DOUBLE-DOWN, INSURANCE, ...)
    * @param amount
    * @return
    */
  def internalWithdraw(user: User, gameId: Int, commandId: Int, amount: BigDecimal): ResultCode = {
    accountDao.findAccount(user.id) match {
      case Some(account) =>
        try {
          checkMoney(account, amount)
          internalUpdate(account, gameId, commandId, -amount)
          ResultCode(DaoStatusCode.OK, "Internal withdrawal successful")
        } catch {
          case e: NotEnoughMoneyException => ResultCode(DaoStatusCode.ERROR, s"Not enough money: balance=${account.amount}, requested=$amount")
          // TODO: Send email
          case e: Exception => ResultCode(DaoStatusCode.SERVER_ERROR, s"Internal withdrawal failed: ${e.getLocalizedMessage}")
        }
      case None => accountNotFound
    }
  }

  // TODO: In the future reason should be created outside of the AccountService
  def internalDeposit(user: User, gameId: Int, commandId: Int, amount: BigDecimal): ResultCode = {
    accountDao.findAccount(user.id) match {
      case Some(account) =>
        try {
          internalUpdate(account, gameId, commandId, amount)
          ResultCode(DaoStatusCode.OK, "Internal deposit successful")
        } catch {
          case e: NotEnoughMoneyException => ResultCode(DaoStatusCode.ERROR, s"Not enough money: balance=${account.amount}, requested=$amount")
          // TODO: Send email
          case e: Exception => ResultCode(DaoStatusCode.SERVER_ERROR, s"Internal deposit failed: ${e.getLocalizedMessage}")
        }
      case None => accountNotFound
    }
  }

  def externalDeposit(user: User, amount: BigDecimal): ResultCode = {
    accountDao.findAccount(user.id) match {
      case Some(account) =>
        try {
          checkMoney(account, amount)

          // TODO: Use ReasonFactory
          val reason = "{}"
          val newTransaction = Transaction(-1, account.id, amount, commandService.EXTERNAL_DEPOSIT, reason, NOT_CONFIRMED, -1)
          transactionDao.insertTransaction(newTransaction)

          accountDao.updateMoney(account.id, amount)

          val confirmedTransaction = Transaction(-1, account.id, amount, commandService.EXTERNAL_DEPOSIT, reason, CONFIRMED, -1)
          transactionDao.insertTransaction(confirmedTransaction)

          ResultCode(DaoStatusCode.OK, "External deposit successful")
        } catch {
          // TODO: Send email
          case e: Exception => ResultCode(DaoStatusCode.SERVER_ERROR, s"External deposit failed: ${e.getLocalizedMessage}")
        }
      case None => accountNotFound
    }
  }

  // TODO: Check if it is better to have user as parameter, or account.
  def externalWithdraw(user: User, payoutAddress: String, amount: BigDecimal): ResultCode = {
    // TODO: Make sure user exists, it has valid id, some checks since this is very important
    // TODO: If user is sent as parameter we have to get account from the database

    // Withdrawing money from the account has to be transaction that updates the account and inserts transaction
    // TODO: Check how to use this localTx in this case, since I am not really sure what is going to happen if it fails
    // TODO: Refactor this in multiple functions
    // Find user's account
    accountDao.findAccount(user.id) match {
      case Some(account) =>
        try {
          // Check if user has enough money in the account
          checkMoney(account, amount)
          // Create transaction and insert it in the database
          // TODO: Create ReasonFactory that will output JSON for each transaction
          val reason = "{}";
          // NOTE: Ammount is -ammount since this transaction withdraws the money from the account
          val newTransaction = Transaction(-1, account.id, -amount, commandService.EXTERNAL_WITHDRAWAL, reason, NOT_CONFIRMED, -1)
            transactionDao.insertTransaction(newTransaction)

          // Update account
          accountDao.updateMoney(account.id, -amount)

          // TODO: What happens if this fails since wallet might fail to send money and transaction is inserted in the database
          // TODO: Send report via email if this happens

          // TODO: This has to be future and should wait for the result, therefore this Try and Catch block will be
          /// TODO: Future onComplete { case Success(_), case Failure(_) }
          walletService.sendMoney(payoutAddress, amount)
          // TODO: IF SEND MONEY FAILED:
          // TODO: SEND EMAIL REPORT, THIS IS VERY IMPORTANT SINCE USER WILL HAVE LESS MONEY IN THE ACCOUNT AND
          // TODO: ALTHOUGH WE HAVEN'T SENT THE MONEY

          // Confirm transaction
          val confirmedTransaction = Transaction(-1, account.id, -amount, commandService.EXTERNAL_WITHDRAWAL, reason, CONFIRMED, -1)
          transactionDao.insertTransaction(confirmedTransaction)

          ResultCode(DaoStatusCode.OK, "Money withdrawn successfully!")
        } catch {
          // TODO: Send email report
          case e: Exception => ResultCode(DaoStatusCode.SERVER_ERROR, s"External withdrawal failed: ${e.getLocalizedMessage}")
        }
      // TODO: This should never happen and if it happens send email report mark as important
      case None => accountNotFound
    }
  }
}
