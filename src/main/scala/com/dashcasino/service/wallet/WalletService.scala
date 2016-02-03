package com.dashcasino.service.wallet

import org.joda.time.DateTime
import akka.actor._

import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by freezing on 1/30/16.
  */
class WalletService(implicit system: ActorSystem, timeout: Timeout) {
  // WalletService should have only one Actor for now
  val actor = system.actorOf(Props[WalletServiceActor], name = "walletServiceActor")

  def generateDepositAddress: String = {
    Await.result(actor ? GenerateNewAddress, 5.seconds).asInstanceOf[String]
  }

  def sendMoney(payoutAddress: String, amount: BigDecimal): Unit = {
    Await.result(actor ? SendMoney(payoutAddress, amount), 30.seconds)
  }
}
