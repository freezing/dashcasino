package com.dashcasino.service.blackjack.logic

import com.dashcasino.model.{BlackjackHandStatus, BlackjackHands, BlackjackGameState}

import argonaut._
import Argonaut._

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionStand { self: BlackjackStateTransition =>
  def nextStateAfterStand(blackjackGameState: BlackjackGameState, userHands: BlackjackHands, handStatus: String = BlackjackHandStatus.STANDING): BlackjackGameState = {
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
    blackjackGameState.copy(userHand = newUserHands.asJson.spaces2)
  }
}
