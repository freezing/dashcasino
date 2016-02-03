package com.dashcasino.service.wallet

import org.joda.time.DateTime
import akka.actor._

import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by freezing on 1/30/16.
  */
class WalletService(implicit system: ActorSystem, timeout: Timeout) {
  // WalletService should have only one Actor for now
  val actor = system.actorOf(Props[WalletServiceActor])

  def generateDepositAddress: String = {
    Await.result(actor ? GenerateNewAddress, 5.seconds) match {
      case Success(res) => res.asInstanceOf[String]
      case Failure(t) => throw t
    }
  }

  def sendMoney(payoutAddress: String, amount: BigDecimal): Unit = {
    Await.result(actor ? SendMoney(payoutAddress, amount), 30.seconds) match {
      case Success(_) =>
      case Failure(t) => throw t
    }
  }
}
