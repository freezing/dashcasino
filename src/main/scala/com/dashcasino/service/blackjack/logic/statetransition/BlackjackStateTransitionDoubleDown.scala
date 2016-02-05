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
  def nextStateAfterDoubleDown(oldState: BlackjackGameState, deck: BlackjackDeck, userHands: BlackjackHands, dealerHand: BlackjackHand, nextCard: Int)
                       (implicit blackjackCardDao: BlackjackCardSqlDao, commandService: CommandService, statusCodeService: StatusCodeService): BlackjackGameState = {
    val hitState = nextStateAfterHit(oldState, deck, userHands, dealerHand, nextCard)
    val afterHitUserHands = hitState.userHand

    if (hitState.statusCode == statusCodeService.blackjackRoundRunning.code) nextStateAfterStand(hitState, afterHitUserHands, BlackjackHandStatus.DOUBLE_DOWN)
    else hitState // THIS PROBABLY MEANS THAT HAND IS BUSTED
  }
}
