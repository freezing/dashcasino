package com.dashcasino

import argonaut._, Argonaut._

package object model {
  class BlackjackHandStatus extends Enumeration {
    val OPEN = Value("OPEN")
    val BUSTED = Value("BUSTED")
    val DOUBLE_DOWN = Value("DOUBLE-DOWN")
    val STANDING = Value("STANDING")
  }
  object BlackjackHandStatus extends BlackjackHandStatus

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
  case class BlackjackDeck(id: Int, order: String, serverSeed: String, clientSeed: String)

  // These two are not connected to any tables. They will just represent UserHand and DealerHand in a nice
  // Case class format using argonaut, and convert hands to JSON ready string for SQL

  // Status can be: OPEN, BUSTED, STANDING, DOUBLE-DOWN (if double-downed but busted it will be BUSTED)
  case class BlackjackHand(hand: List[BlackjackCard], status: BlackjackHandStatus, money: BigDecimal)
  case class BlackjackHands(hands: List[BlackjackHand])

  // Implicit JSON codec for Argonaut
  implicit def BlackjackCardCodecJson = casecodec10(BlackjackCard.apply, BlackjackCard.unapply)("id", "code", "rankCode", "rankName", "rankLetter", "suitName", "suitLetter", "suitCode", "primaryValue", "secondaryValue")
  implicit def BlackjackHandCodecJson = casecodec3(BlackjackHand.apply, BlackjackHand.unapply)("hand", "status", "money")
  implicit def BlackjackHandsCodecJson = casecodec1(BlackjackHands.apply, BlackjackHands.unapply)("hands")
}
