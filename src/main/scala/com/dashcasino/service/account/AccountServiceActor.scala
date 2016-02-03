package com.dashcasino.service.account

import akka.actor.Actor
import com.dashcasino.dao.sql.{AccountSqlDao, TransactionSqlDao}
import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.exception.NotEnoughMoneyException
import com.dashcasino.model.{Account, Transaction, User}
import com.dashcasino.service.CommandService
import com.dashcasino.service.account.logic._
import com.dashcasino.service.wallet.WalletService

/**
  * Created by freezing on 1/30/16.
  */
class AccountServiceActor(implicit val accountDao: AccountSqlDao, transactionDao: TransactionSqlDao,
                     walletService: WalletService, commandService: CommandService) extends Actor with CreateAccountActorLogic
    with InternalWithdrawalActorLogic with InternalDepositActorLogic with ExternalDepositActorLogic with ExternalWithdrawalActorLogic {
  val accountNotFound = ResultCode(DaoStatusCode.SERVER_ERROR, "Account not found")

  val NOT_CONFIRMED = 0
  val CONFIRMED = 1

  // TODO: Maybe use amount - EPS?
  protected def checkMoney(account: Account, amount: BigDecimal) = {
    if (account.amount < amount) throw new NotEnoughMoneyException(s"Not enough money: ${account.amount}")
  }

  protected def internalUpdate(account: Account, amount: BigDecimal, reason: String) = {
    val newTransaction = Transaction(-1, account.id, amount, reason, NOT_CONFIRMED, -1)
    transactionDao.insertTransaction(newTransaction)

    // Update the account
    accountDao.updateMoney(account.id, amount)

    val confirmedTransaction = Transaction(-1, account.id, amount, reason, CONFIRMED, -1)
    transactionDao.insertTransaction(confirmedTransaction)
  }

  def receive = {
    case msg: InternalWithdrawal => sender ! internalWithdrawal(msg)
    case msg: InternalDeposit => sender ! internalDeposit(msg)
    case msg: ExternalWithdrawal => sender ! externalWithdrawal(msg)
    case msg: ExternalDeposit => sender ! externalDeposit(msg)
    case msg: User => sender ! createAccount(msg)
    case unknown => throw new IllegalArgumentException(s"Unknown message: $unknown")
  }
}
