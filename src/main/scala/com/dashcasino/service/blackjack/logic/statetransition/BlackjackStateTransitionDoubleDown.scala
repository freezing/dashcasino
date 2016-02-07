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
    val hitState = nextStateAfterHit(oldState, deck, nextCard)
    val doubleDownState = {
      if (hitState.statusCode == statusCodeService.blackjackRoundRunning.code) nextStateAfterStand(hitState, deck, BlackjackHandStatus.DOUBLE_DOWN)
      else hitState
    }
    doubleDownState.copy(commandCode = commandService.blackjackDoubleDown.code)
  }
}
