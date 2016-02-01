package com.dashcasino.service

import com.dashcasino.model.User

/**
  * Created by freezing on 2/1/16.
  */
package object account {
  // Messages
  case class InternalWithdrawal(userId: Int, gameId: Int, commandId: Int, amount: BigDecimal, reason: String)
  case class InternalDeposit(userId: Int, gameId: Int, commandId: Int, amount: BigDecimal, reason: String)
  case class ExternalWithdrawal(userId: Int, payoutAddress: String, amount: BigDecimal)
  case class ExternalDeposit(userId: Int, amount: BigDecimal, reason: String)
}
