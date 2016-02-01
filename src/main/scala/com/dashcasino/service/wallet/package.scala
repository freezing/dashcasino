package com.dashcasino.service

/**
  * Created by freezing on 2/1/16.
  */
package object wallet {
  // Messages
  case class GenerateNewAddress()
  case class SendMoney(payoutAddress: String, amount: BigDecimal)
}
