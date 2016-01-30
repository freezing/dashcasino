package com.dashcasino.service

import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.dao.sql.{TransactionSqlDao, AccountSqlDao}
import com.dashcasino.model.{Account, Transaction, User}
import scalikejdbc.DB

/**
  * Created by freezing on 1/30/16.
  */
class AccountService(implicit val accountDao: AccountSqlDao, transactionDao: TransactionSqlDao,
                     walletService: WalletService, commandService: CommandService) {
  // TODO: Check if it is better to have user as parameter, or account.
  def externalWithdraw(user: User, payoutAddress: String, amount: BigDecimal): ResultCode = {
    // TODO: Make sure user exists, it has valid id, some checks since this is very important
    // TODO: If user is sent as parameter we have to get account from the database


    // Withdrawing money from the account has to be transaction that updates the account and inserts transaction
    // TODO: Check how to use this localTx in this case, since I am not really sure what is going to happen if it fails
    DB localTx { implicit session =>
      // TODO: Refactor this in multiple functions
      // Find user's account
      accountDao.findAccount(user.id) match {
        case Some(account) =>
          // Check if user has enough money in the account
          // TODO: Maybe use amount - EPS?
          if (account.amount < amount) ResultCode(DaoStatusCode.ERROR, s"Not enough money: ${account.amount}")
          else {
            // Create transaction and insert it in the database
            // TODO: Create ReasonFactory that will output JSON for each transaction
            val reason = "{}";
            // NOTE: Ammount is -ammount since this transaction withdraws the money from the account
            val transaction = Transaction(-1, account.id, -amount, commandService.EXTERNAL_WITHDRAWAL, reason, -1)
            try {
              transactionDao.insertTransaction(transaction)
              // TODO: What happens if this fails since wallet might fail to send money and transaction is inserted in the database
              // TODO: Send report via email if this happens
              try {
                // TODO: This has to be future and should wait for the result, therefore this Try and Catch block will be
                /// TODO: Future onComplete { case Success(_), case Failure(_) }
                walletService.sendMoney(payoutAddress, amount)
              } catch {
                // TODO: SEND EMAIL REPORT, THIS IS VERY IMPORTANT SINCE USER WILL HAVE LESS MONEY IN THE ACCOUNT AND
                // TODO: ALTHOUGH WE HAVEN'T SENT THE MONEY
                case e: Exception => ResultCode(DaoStatusCode.ERROR, "Failed to send money from wallet")
              }

              ResultCode(DaoStatusCode.OK, "Money withdrawn successfully!")
            } catch {
              // TODO: Send email report
              case e: Exception => ResultCode(DaoStatusCode.ERROR, "Failed to insert transaction in database")
            }
          }
        // TODO: This should never happen and if it happens send email report mark as important
        case None => ResultCode(DaoStatusCode.ERROR, "Account not found")
      }
    }
  }
}
