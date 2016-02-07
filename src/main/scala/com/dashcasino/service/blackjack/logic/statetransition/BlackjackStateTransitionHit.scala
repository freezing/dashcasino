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
    // Add new card to the first OPEN hand (the one matching idx)
    val newUserBlackjackHands = findAndUpdateFirstOpenHand(oldState.userHand, (hand: BlackjackHand) => {
      val newCards = hand.cards ++ List(blackjackCardDao.findBlackjackCard(nextCard))
      val newStatus = newStatusAfterHit(newCards)
      hand.copy(cards = newCards, status = newStatus)
    })
    val statusCode = getGameStatus(newUserBlackjackHands).code

    val newDealerHand = {
      // Dealer shouldn't draw after, hit unless user is standing - i.e. has exactly 21 which is autostand
      val hasStanding = newUserBlackjackHands.hands exists { _.status == BlackjackHandStatus.STANDING }
      if (statusCodeService.isGameFinished(statusCode) && hasStanding) getFinalDealerHand(newUserBlackjackHands, oldState.dealerHand, deck)
      else oldState.dealerHand
    }
    val userHandsOutcome = userHandsWithOutcome(newUserBlackjackHands, newDealerHand, statusCode)
    oldState.copy(userHand = userHandsOutcome, dealerHand = newDealerHand, commandCode = commandService.blackjackHit.code, statusCode = statusCode)
  }

  def newStatusAfterHit(newCards: List[BlackjackCard]): String = {
    if (newCards.isEmpty) BlackjackHandStatus.EMPTY
    else if (isOpen(newCards)) BlackjackHandStatus.OPEN
    else if (isBusted(newCards)) BlackjackHandStatus.BUSTED
    else BlackjackHandStatus.STANDING // THIS IS THE CASE WHEN HAND VALUE IS 21 EXACTLY
  }
}
