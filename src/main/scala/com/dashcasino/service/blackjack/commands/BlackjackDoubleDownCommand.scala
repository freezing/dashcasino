package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.BlackjackGameStateSqlDao
import com.dashcasino.exception.CantHitException
import com.dashcasino.model.{BlackjackHands, BlackjackGameState}
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import com.dashcasino.service.blackjack.{BlackjackDoubleDown, BlackjackService, BlackjackDeckService}
import com.dashcasino.service.CommandService

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackDoubleDownCommand { self: BlackjackServiceActor =>
  def `double-down`(msg: BlackjackDoubleDown)
    (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao, commandService: CommandService): BlackjackHands = {
      val (userId, gameId) = BlackjackDoubleDown.unapply(msg).get
      val game = blackjackGameDao.findBlackjackGame(gameId).get

      checkAuthorization(userId, game)

      // Make sure that User can double down
      val gameState = blackjackGameStateDao.findLastBlackjackGameState(gameId).get
      // TODO: CantPlayException should be used everywhere
      if (!canDoubleDown(gameState)) throw new CantHitException

      // Get next game state and insert it in the database
      val nextGameState = getNextState(game.blackjackDeckId, gameState, commandService.blackjackDoubleDown)
      blackjackGameStateDao.insertBlackjackGameState(nextGameState)

      // Return user's hands
      userHands(nextGameState)
  }

  // TODO: TO USE DOUBLE DOWN USER MUST WITHDRAW SAME AMOUNT OF MONEY HE BET
  def canDoubleDown(state: BlackjackGameState): Boolean = canHit(state)
}
