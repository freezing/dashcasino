package com.dashcasino.service.blackjack.logic

import com.dashcasino.dao.sql.BlackjackCardSqlDao
import com.dashcasino.model._

import argonaut._
import Argonaut._
import com.dashcasino.service.CommandService

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionHit { self: BlackjackStateTransition =>
  def nextStateAfterHit(oldState: BlackjackGameState, deck: BlackjackDeck, userHands: BlackjackHands, dealerHand: BlackjackHand, nextCard: Int)
                       (implicit blackjackCardDao: BlackjackCardSqlDao): BlackjackGameState = {

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
    val newUserBlackjackHands = BlackjackHands(newHands)
    // TODO: UPDATE STATUS CODE

    val newDealerHand = {
      if (isGameFinished(newUserBlackjackHands)) getFinalDealerHand(newUserBlackjackHands, dealerHand, deck)
      else dealerHand
    }
    oldState.copy(userHand = newUserBlackjackHands.asJson.spaces2, dealerHand = newDealerHand.asJson.spaces2, commandId = CommandService.BLACKJACK_HIT)
  }
}
