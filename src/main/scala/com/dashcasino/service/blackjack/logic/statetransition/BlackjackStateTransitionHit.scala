package com.dashcasino.service.blackjack.logic.statetransition

import argonaut.Argonaut._
import argonaut._
import com.dashcasino.dao.sql.BlackjackCardSqlDao
import com.dashcasino.model._
import com.dashcasino.service.{StatusCodeService, CommandService}

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionHit { self: BlackjackStateTransition =>
  def nextStateAfterHit(oldState: BlackjackGameState, deck: BlackjackDeck, nextCard: Int)
                       (implicit blackjackCardDao: BlackjackCardSqlDao, commandService: CommandService, statusCodeService: StatusCodeService): BlackjackGameState = {
    val userHands = oldState.userHand
    val dealerHand = oldState.dealerHand
    // Find user's hand that is open with priority for the first one
    // TODO: IMPORTANT, REFACTOR THIS CODE SO IT USES SCALA BUILT-IN METHODS INSTEAD OF FOR COMPREHENSION OVER INDICES

    // Add new card to the first OPEN hand (the one matching idx)
    var foundOpenHand = false
    val newHands = userHands.hands map { hand =>
      val newCards = {
        if (!foundOpenHand && hand.status == BlackjackHandStatus.OPEN) {
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
        if (newCards.isEmpty) BlackjackHandStatus.EMPTY
        else if (isOpen(newCards)) BlackjackHandStatus.OPEN
        else if (isBusted(newCards)) BlackjackHandStatus.BUSTED
        else BlackjackHandStatus.STANDING // THIS IS THE CASE WHEN HAND VALUE IS 21 EXACTLY
      }
      hand.copy(cards = newCards, status = newStatus)
    }
    val newUserBlackjackHands = BlackjackHands(newHands)
    val statusCode = getGameStatus(newUserBlackjackHands).code

    val newDealerHand = {
      // Dealer shouldn't draw after, hit unless user is standing - i.e. has exactly 21 which is autostand
      // TODO: This should be implemented as HIT COMMAND then calling STAND command if hand value is 21
      val hasStanding = newUserBlackjackHands.hands exists { _.status == BlackjackHandStatus.STANDING }
      if (statusCodeService.isGameFinished(statusCode) && hasStanding) getFinalDealerHand(newUserBlackjackHands, dealerHand, deck)
      else dealerHand
    }

    val userHandsOutcome = userHandsWithOutcome(newUserBlackjackHands, newDealerHand)

    oldState.copy(userHand = userHandsOutcome, dealerHand = newDealerHand, commandCode = commandService.blackjackHit.code, statusCode = statusCode)
  }
}
