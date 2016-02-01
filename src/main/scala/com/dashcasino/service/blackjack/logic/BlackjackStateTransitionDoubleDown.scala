package com.dashcasino.service.blackjack.logic

import com.dashcasino.dao.sql.BlackjackCardSqlDao
import com.dashcasino.model._
import com.dashcasino.service.CommandService
import com.dashcasino.service.blackjack.BlackjackService

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionDoubleDown {  self: BlackjackService =>
  def nextStateAfterDoubleDown(oldState: BlackjackGameState, deck: BlackjackDeck, userHands: BlackjackHands, dealerHand: BlackjackHand, nextCard: Int)
                       (implicit blackjackCardDao: BlackjackCardSqlDao): BlackjackGameState = {
    val hitState = nextStateAfterHit(oldState, deck, userHands, dealerHand, nextCard)
    val afterHitUserHands = decodeUserHands(hitState.userHand)

    if (canStand(hitState)) nextStateAfterStand(hitState, afterHitUserHands, BlackjackHandStatus.DOUBLE_DOWN)
    else hitState // THIS PROBABLY MEANS THAT HAND IS BUSTED
  }
}
