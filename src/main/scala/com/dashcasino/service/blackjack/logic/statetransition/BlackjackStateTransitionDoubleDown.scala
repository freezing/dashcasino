package com.dashcasino.service.blackjack.logic.statetransition

import com.dashcasino.dao.sql.BlackjackCardSqlDao
import com.dashcasino.model._
import com.dashcasino.service.{StatusCodeService, CommandService}
import com.dashcasino.service.blackjack.BlackjackService
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionDoubleDown {  self: BlackjackServiceActor =>
  def nextStateAfterDoubleDown(oldState: BlackjackGameState, deck: BlackjackDeck, nextCard: Int)
                       (implicit blackjackCardDao: BlackjackCardSqlDao, commandService: CommandService, statusCodeService: StatusCodeService): BlackjackGameState = {
    val newUserBlackjackHands = findAndUpdateFirstOpenHand(oldState.userHand, (hand: BlackjackHand) => {
      val newCards = hand.cards ++ List(blackjackCardDao.findBlackjackCard(nextCard))
      val newStatus = newStatusAfterDoubleDown(newCards)
      // Double the money
      hand.copy(cards = newCards, status = newStatus, money = hand.money * 2)
    })

    val statusCode = getGameStatus(newUserBlackjackHands).code

    val newDealerHand = {
      // Dealer shouldn't draw after, hit unless user is standing - i.e. has exactly 21 which is autostand
      val hasDoubleDown = newUserBlackjackHands.hands exists { _.status == BlackjackHandStatus.DOUBLE_DOWN }
      if (statusCodeService.isGameFinished(statusCode) && hasDoubleDown) getFinalDealerHand(newUserBlackjackHands, oldState.dealerHand, deck)
      else oldState.dealerHand
    }

    val userHandsOutcome = userHandsWithOutcome(newUserBlackjackHands, newDealerHand, statusCode)
    oldState.copy(userHand = userHandsOutcome, dealerHand = newDealerHand, commandCode = commandService.blackjackHit.code, statusCode = statusCode)
  }
  def newStatusAfterDoubleDown(cards: List[BlackjackCard]): String = {
    // Status is the same as HIT status. Exception is when the status is OPEN or STANDING, it will become DOUBLE-DOWN
    newStatusAfterHit(cards) match {
      case BlackjackHandStatus.OPEN | BlackjackHandStatus.STANDING => BlackjackHandStatus.DOUBLE_DOWN
      case other => other
    }
  }
}
