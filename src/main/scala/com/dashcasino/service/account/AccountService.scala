package com.dashcasino.service.account

import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import akka.pattern.ask
import com.dashcasino.dao.sql.{AccountSqlDao, TransactionSqlDao}
import com.dashcasino.model.User
import com.dashcasino.service.CommandService
import com.dashcasino.service.wallet.WalletService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Success, Failure}

/**
  * Created by freezing on 2/1/16.
  * One actor can handle multiple accounts. All messages that are related to Account must be handled by the same Actor.
  */
// TODO: There should be a pool of actors and accounts should be assigned to Actors
// TODO: For now it can be one, but it is essential to have more
class AccountService(implicit system: ActorSystem, timeout: Timeout, walletService: WalletService, commandService: CommandService, transactionSqlDao: TransactionSqlDao, accountSqlDao: AccountSqlDao) {
  val actor = system.actorOf(Props(new AccountServiceActor), "accountServiceActor")

  def createAccount(user: User): Unit = {
    Await.result(actor ? user, 30.seconds)
  }

  def internalDeposit(msg: InternalDeposit): Unit = {
    Await.result(actor ? msg, 10.seconds)
  }

  def internalWithdrawal(msg: InternalWithdrawal): Unit = {
    Await.result(actor ? msg, 10.seconds)
  }

  def externalDeposit(msg: ExternalDeposit): Unit = {
    Await.result(actor ? msg, 10.seconds)
  }

  def externalWithdrawal(msg: ExternalWithdrawal): Unit = {
    Await.result(actor ? msg, 30.seconds)
  }
}
