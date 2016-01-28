package com.dashcasino

import java.sql.Timestamp

import scala.collection.JavaConversions._

package object models {
  // TODO: Figure out if case classes can use apply methods
  case class User(id: Int, email: String, passwordHash: String, timestamp: Long)
  case class Account(id: Int, userId: Int, depositAddress: String, amount: BigDecimal)
  case class Transaction(id: Int, accountId: Int, amount: BigDecimal, commandId: Int, reason: String, timestamp: Long)
  case class BlackjackGame(id: Int, userId: Int, timestamp: Long)
  case class BlackjackGameState(id: Int, userId: Int, gameId: Int, userHand: String, dealerHand: String, description: String, commandId: Int, statusCodeId: Int, timestamp: Long)
  case class Command(id: Int, name: String)
  case class StatusCode(id: Int, value: String)

  // This card is user only for the blackjack game since it has all the required info for the game
  // Value represents card value in the blackjack game
  // NOTE: Primary and Secondary values are for the Ace
  case class BlackjackCard(id: Int, code: Int, rankCode: Int, rankName: String, rankLetter: Char, suitName: String, suitCode: Int, primaryValue: Int, secondaryValue: Int)

  case class BlackjackHand(hand: List[BlackjackCard])
  case class BlackjackHands(hands: List[BlackjackHand])
}
