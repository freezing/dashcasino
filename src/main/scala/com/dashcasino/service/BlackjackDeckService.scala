package com.dashcasino.service

import com.dashcasino.dao.sql.{BlackjackDeckSqlDao, BlackjackCardSqlDao}
import com.dashcasino.model._
import com.sun.javaws.exceptions.InvalidArgumentException
import spray.util.NotImplementedException

import argonaut._
import Argonaut._

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackDeckService(implicit val blackjackDeckDao: BlackjackDeckSqlDao, blackjackCardSqlDao: BlackjackCardSqlDao) {
  type JsonString = String


  def getCard(code: Int) = blackjackCardSqlDao.findBlackjackCard(code)


  // TODO: Implement logic that returns hands
  def getUserHands(blackjackDeck: BlackjackDeck, gameStateOption: Option[BlackjackGameState]): BlackjackHands = {
    ???
  }

  def getDealerHand(blackjackDeck: BlackjackDeck, gameStateOption: Option[BlackjackGameState]): BlackjackHand = {
    ???
  }

  def getUserHands(blackjackDeckId: Int, gameStateOption: Option[BlackjackGameState]): BlackjackHands = {
    blackjackDeckDao.findBlackjackDeck(blackjackDeckId) match {
      case Some(deck) => getUserHands(deck, gameStateOption)
      // TODO: SEND EMAIL REPORT AS THIS SHOULD NEVER HAPPEN AND THROW DeckNotFoundException
      case None => throw new Exception("Deck not found")
    }
  }

  def getDealerHand(blackjackDeckId: Int, gameStateOption: Option[BlackjackGameState]): BlackjackHand = {
    blackjackDeckDao.findBlackjackDeck(blackjackDeckId) match {
      case Some(deck) => getDealerHand(deck, gameStateOption)
      // TODO: SEND EMAIL REPORT AS THIS SHOULD NEVER HAPPEN AND THROW DeckNotFoundException
      case None => throw new Exception("Deck not found")
    }
  }

  def getNextState(blackjackDeckId: Int, blackjackGameState: Option[BlackjackGameState], commandId: Int): BlackjackGameState = blackjackGameState match {
    case Some(x) => getNextState(blackjackDeckId, x, commandId)
    case None =>
      // TODO: IMplement logic for start of the game state
      ???
  }

  def getNextState(blackjackDeckId: Int, blackjackGameState: BlackjackGameState, commandId: Int): BlackjackGameState = {
    val deck = blackjackDeckDao.findBlackjackDeck(blackjackDeckId) match {
      case Some(x) => x
        // TODO: Email report
      case None => throw new IllegalArgumentException(s"Invalid blackjackDeckId: $blackjackDeckId")
    }

    // Draw next card for the given state
    val dealerHand = blackjackGameState.dealerHand.decodeOption[BlackjackHand] match {
      case Some(x) => x
        // TODO: Send email report
      case None => throw new IllegalStateException(s"Invalid blackjackDealerHand json: ${blackjackGameState.dealerHand}")
    }

    val userHands = blackjackGameState.userHand.decodeOption[BlackjackHands] match {
      case Some(x) => x
        // TODO: Send email report
      case None => throw new IllegalStateException(s"Invalid blackjackUserHands json: ${blackjackGameState.userHand}")
    }

    val cards = deck.order.cards
    val nextCard = cards(dealerHand.cards.length + userHands.hands.head.cards.length + userHands.hands(1).cards.length)

    commandId match {
      case CommandService.BLACKJACK_HIT =>
        // Find user's hand that is open with priority for the first one
        // TODO: IMPORTANT, REFACTOR THIS CODE SO IT USES SCALA BUILT-IN METHODS INSTEAD OF FOR COMPREHENSION OVER INDICES

        // Add new card to the first OPEN hand (the one matching idx)
        var foundOpenHand = false
        val newHands = userHands.hands map { hand =>
          val newCards = {
            if (!foundOpenHand && hand.status == "OPEN") {
              foundOpenHand = true
              val bjCard = blackjackCardSqlDao.findBlackjackCard(nextCard) match {
                case Some(card) => card
                case None => throw new IllegalStateException(s"Unknown card code: $nextCard")
              }
              hand.cards ++ List(bjCard)
            } else {
              hand.cards
            }
          }
          // Update cards, status, money (stays the same)
          val newStatus = {
            // TODO: Implement logic for status after HIT
            // If at least one value is open
            if (isOpen(newCards)) BlackjackHandStatus.OPEN
            else if (isBusted(newCards)) BlackjackHandStatus.BUSTED
            else BlackjackHandStatus.STANDING // THIS IS THE CASE WHEN HAND VALUE IS 21 EXACTLY
          }
          hand.copy(cards = newCards, status = newStatus)
        }
//        BlackjackGameState(-1, blackjackGameState.gameId, newHands)
        ???
      // TODO: Implement logic for HIT
      case unknownCommand => throw new NotImplementedException(s"Command $unknownCommand hasn't been implemented yet!")
    }
  }

  def isOpen(cards: List[BlackjackCard]): Boolean = {
    val (v1, v2) = calculateHandValue(cards)
    isOpen(v1) || isOpen(v2)
  }

  def isOpen(v: Int): Boolean = v >= 0 && v < 21

  def isBusted(cards: List[BlackjackCard]): Boolean = {
    val (v1, v2) = calculateHandValue(cards)
    isBusted(v1) && isBusted(v2)
  }

  def isBusted(v: Int) = v < 0 || v > 21

  def calculateHandValue(cards: List[BlackjackCard]): (Int, Int) = {
    ???
  }
}
