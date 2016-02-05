package com.dashcasino.service.blackjack.logic.statetransition

import argonaut.Argonaut._
import argonaut._
import com.dashcasino.model.{BlackjackGameState, BlackjackHandStatus, BlackjackHands}
import com.dashcasino.service.StatusCodeService

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionStand { self: BlackjackStateTransition =>
  def nextStateAfterStand(blackjackGameState: BlackjackGameState, userHands: BlackjackHands, handStatus: String = BlackjackHandStatus.STANDING)
                         (implicit statusCodeService: StatusCodeService): BlackjackGameState = {
    val newHands = {
      var updatedOnce = false
      userHands.hands map { hand =>
        // TODO: DEFINITELY REFACTOR xD
        if (!updatedOnce && hand.status == BlackjackHandStatus.OPEN) {
          updatedOnce = true
          hand.copy(status = handStatus)
        } else {
          hand
        }
      }
    }
    val newUserHands = userHands.copy(hands = newHands)
    val statusCode = getGameStatus(newUserHands).code
    blackjackGameState.copy(userHand = newUserHands, statusCode = statusCode)
  }
}
