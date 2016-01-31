package com.dashcasino.service.blackjack.logic

import com.dashcasino.dao.sql.{BlackjackCardSqlDao, BlackjackDeckSqlDao}
import com.dashcasino.model._
import com.dashcasino.service.{BlackjackService, CommandService}
import spray.util.NotImplementedException

import argonaut._
import Argonaut._

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransition { self: BlackjackService =>
  def getInitialState(blackjackDeckId: Int)(implicit blackjackDeckDao: BlackjackDeckSqlDao, blackjackCardDao: BlackjackCardSqlDao): BlackjackGameState = {

    ???
  }

  def getNextState(blackjackDeckId: Int, blackjackGameState: BlackjackGameState, commandId: Int)
                  (implicit blackjackDeckDao: BlackjackDeckSqlDao, blackjackCardDao: BlackjackCardSqlDao): BlackjackGameState = {
    val deck = getDeck(blackjackDeckId)

    val dealerHand = decodeDealerHand(blackjackGameState.dealerHand)
    val userHands = decodeUserHands(blackjackGameState.userHand)

    val cards = deck.order.cards
    // Draw next card for the state
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
              val bjCard = blackjackCardDao.findBlackjackCard(nextCard) match {
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
