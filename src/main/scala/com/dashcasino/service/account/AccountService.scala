package com.dashcasino.service.account

import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by freezing on 2/1/16.
  * One actor can handle multiple accounts. All messages that are related to Account must be handled by the same Actor.
  */
// TODO: There should be a pool of actors and accounts should be assigned to Actors
// TODO: For now it can be one, but it is essential to have more
class AccountService(implicit system: ActorSystem, timeout: Timeout) {
  val actor = system.actorOf(Props[AccountServiceActor], "accountServiceActor")

  def internalDeposit(msg: InternalDeposit): Unit = {
    // TODO: Unit methods will all have the same PartialFunction, it should be GLOBAL
    actor ? msg onComplete {
      case Success(_) =>
      case Failure(t) => throw t
    }
  }

  def internalWithdrawal(msg: InternalWithdrawal): Unit = {
    actor ? msg onComplete {
      case Success(_) =>
      case Failure(t) => throw t
    }
  }

  def externalDeposit(msg: ExternalDeposit): Unit = {
    actor ? msg onComplete {
      case Success(_) =>
      case Failure(t) => throw t
    }
  }

  def externalWithdrawal(msg: ExternalWithdrawal): Unit = {
    actor ? msg onComplete {
      case Success(_) =>
      case Failure(t) => throw t
    }
  }
}