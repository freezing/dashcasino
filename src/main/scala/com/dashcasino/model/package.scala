package com.dashcasino

import argonaut._, Argonaut._

package object model {
  object BlackjackHandStatus {
    val OPEN = "OPEN"
    val BUSTED = "BUSTED"
    val DOUBLE_DOWN = "DOUBLE-DOWN"
    val STANDING = "STANDING"
  }

  // TODO: Figure out if case classes can use apply methods
  case class User(id: Int, email: String, passwordHash: String, timestamp: Long)
  case class Account(id: Int, userId: Int, depositAddress: String, amount: BigDecimal)
  case class Transaction(id: Int, accountId: Int, amount: BigDecimal, commandId: Int, reason: String, confirmed: Int, timestamp: Long)
  case class BlackjackGame(id: Int, userId: Int, blackjackDeckId: Int, timestamp: Long)
  case class BlackjackGameState(id: Int, userId: Int, gameId: Int, userHand: String, dealerHand: String, description: String, commandId: Int, statusCodeId: Int, timestamp: Long)
  case class Command(id: Int, name: String)
  case class StatusCode(id: Int, value: String)

  // This card is user only for the blackjack game since it has all the required info for the game
  // Value represents card value in the blackjack game
  // NOTE: Primary and Secondary values are for the Ace
  case class BlackjackCard(id: Int, code: Int, rankCode: Int, rankName: String, rankLetter: String, suitName: String, suitLetter: String, suitCode: Int, primaryValue: Int, secondaryValue: Int)
  case class BlackjackDeckOrder(order: List[Int])
  case class BlackjackDeck(id: Int, order: BlackjackDeckOrder, serverSeed: String, clientSeed: String)

  // These two are not connected to any tables. They will just represent UserHand and DealerHand in a nice
  // Case class format using argonaut, and convert hands to JSON ready string for SQL

  // Status can be: OPEN, BUSTED, STANDING, DOUBLE-DOWN (if double-downed but busted it will be BUSTED)
  case class BlackjackHand(hand: List[BlackjackCard], status: String, money: BigDecimal)
  case class BlackjackHands(hands: List[BlackjackHand])

  // Implicit JSON codec for Argonaut
  implicit def BlackjackDeckOrderCodecJson = casecodec1(BlackjackDeckOrder.apply, BlackjackDeckOrder.unapply)("order")
  implicit def BlackjackCardCodecJson = casecodec10(BlackjackCard.apply, BlackjackCard.unapply)("id", "code", "rankCode", "rankName", "rankLetter", "suitName", "suitLetter", "suitCode", "primaryValue", "secondaryValue")
  implicit def BlackjackHandCodecJson = casecodec3(BlackjackHand.apply, BlackjackHand.unapply)("hand", "status", "money")
  implicit def BlackjackHandsCodecJson = casecodec1(BlackjackHands.apply, BlackjackHands.unapply)("hands")
}
