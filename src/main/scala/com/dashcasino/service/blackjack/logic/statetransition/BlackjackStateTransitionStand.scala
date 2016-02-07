package com.dashcasino.service.blackjack.logic.statetransition

import argonaut.Argonaut._
import argonaut._
import com.dashcasino.model._
import com.dashcasino.service.{CommandService, StatusCodeService}

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionStand { self: BlackjackStateTransition =>
  def nextStateAfterStand(blackjackGameState: BlackjackGameState, deck: BlackjackDeck)
                         (implicit statusCodeService: StatusCodeService, commandService: CommandService): BlackjackGameState = {
    val userHands = blackjackGameState.userHand
    val dealerHand = blackjackGameState.dealerHand

    val newUserBlackjackHands = findAndUpdateFirstOpenHand(userHands, (hand: BlackjackHand) => {
      hand.copy(status = BlackjackHandStatus.STANDING)
    })
    val statusCode = getGameStatus(newUserBlackjackHands).code

    val newDealerHand = {
      if (statusCodeService.isGameFinished(statusCode))
        getFinalDealerHand(newUserBlackjackHands, blackjackGameState.dealerHand, deck)
      else dealerHand
    }

    val userHandsOutcome = userHandsWithOutcome(newUserBlackjackHands, newDealerHand)
    blackjackGameState.copy(userHand = userHandsOutcome, dealerHand = newDealerHand, statusCode = statusCode, commandCode = commandService.blackjackStand.code)
  }
}
