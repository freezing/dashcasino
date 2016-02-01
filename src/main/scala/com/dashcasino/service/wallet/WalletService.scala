package com.dashcasino.service.wallet

import org.joda.time.DateTime
import akka.actor._

import akka.pattern.ask
import akka.util.Timeout
import com.dashcasino.service.wallet

import scala.concurrent.ExecutionContext.Implicits.global


import scala.util.{Failure, Success}

/**
  * Created by freezing on 1/30/16.
  */
class WalletService(implicit system: ActorSystem, timeout: Timeout) {
  // WalletService should have only one Actor for now
  val actor = system.actorOf(Props[WalletServiceActor], name = "walletServiceActor")

  def generateDepositAddress: String = {
    // TODO: Figure out how to do this without var
    var res = ""
    actor ? GenerateNewAddress onComplete {
      case Success(address) => res = address.asInstanceOf[String]
      case Failure(t) => throw t
    }
    res
  }

  def sendMoney(payoutAddress: String, amount: BigDecimal): Unit = {
    actor ? SendMoney(payoutAddress, amount) onComplete {
      case Success(_) =>
      case Failure(t) => throw t
    }
  }
}
