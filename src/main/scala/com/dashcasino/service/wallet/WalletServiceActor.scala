package com.dashcasino.service.wallet

import akka.actor.Actor
import org.joda.time.DateTime

/**
  * Created by freezing on 2/1/16.
  */
class WalletServiceActor extends Actor {
  override def receive = {
    case GenerateNewAddress => sender ! generateNewAddress()
  }

  private def generateNewAddress() = {
    s"WalletAddress_${DateTime.now().getMillis}"
  }
}
