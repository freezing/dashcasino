package com.dashcasino.service.wallet

import akka.actor.Actor
import org.joda.time.DateTime

/**
  * Created by freezing on 2/1/16.
  */
class WalletServiceActor extends Actor {
  override def receive = {
    case GenerateNewAddress => sender ! generateNewAddress()
    case msg: SendMoney => sender ! sendMoney(msg)
  }

  private def generateNewAddress() = {
    s"WalletAddress_${DateTime.now().getMillis}"
  }

  private def sendMoney(msg: SendMoney): Unit = {
    // TODO: Implement send money via wallet or throw an exception if failed
    throw new Exception("Send money not implemented yet!")
  }
}
