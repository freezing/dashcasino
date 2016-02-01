package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.BlackjackGameStateSqlDao
import com.dashcasino.exception.CantHitException
import com.dashcasino.model.{BlackjackHands, BlackjackGameState}
import com.dashcasino.service.blackjack.{BlackjackService, BlackjackDeckService}
import com.dashcasino.service.CommandService

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackDoubleDownCommand { self: BlackjackService =>
  def `double-down`(userId: Int, gameId: Int)
    (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao): BlackjackHands = {
      // Find game
      val game = blackjackGameDao.findBlackjackGame(gameId) match {
        case Some(blackjackGame) => blackjackGame
        case None => throw new Exception("Couldn't find the game")
      }

      checkAuthorization(userId, game)

      // Make sure that User can double down
      val gameState = blackjackGameStateDao.findLastBlackjackGameState(gameId) match {
        case Some(state) => state
        case None => throw new Exception("Can't find state for the game")
      }
      // TODO: All exceptions that are not regular should be logged somewhere
      // TODO: CantPlayException should be used everywhere
      if (!canDoubleDown(gameState)) throw new CantHitException

      // Get next game state and insert it in the database
      val nextGameState = getNextState(game.blackjackDeckId, gameState, CommandService.BLACKJACK_DOUBLEDOWN)
      blackjackGameStateDao.insertBlackjackGameState(nextGameState)

      // Return user's hands
      userHands(nextGameState)
  }

  // TODO: TO USE DOUBLE DOWN USER MUST WITHDRAW SAME AMOUNT OF MONEY HE BET
  def canDoubleDown(state: BlackjackGameState): Boolean = canHit(state)
}
