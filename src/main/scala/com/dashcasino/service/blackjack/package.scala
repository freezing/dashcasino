package com.dashcasino.service

/**
  * Created by freezing on 2/1/16.
  */
package object blackjack {
  case class BlackjackBet(userId: Int, blackjackDeckId: Int, amount: BigDecimal)
  case class BlackjackHit(userId: Int, gameId: Int)
  case class BlackjackStand(userId: Int, gameId: Int)
  case class BlackjackDoubleDown(userId: Int, gameId: Int)
  case class BlackjackSplit(userId: Int, gameId: Int)
  case class BlackjackInsurance(userId: Int, gameId: Int)
}
