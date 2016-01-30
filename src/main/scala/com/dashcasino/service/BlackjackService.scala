package com.dashcasino.service

import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.CantHitException
import com.dashcasino.model.{BlackjackHands, BlackjackDeck, BlackjackGameState, BlackjackGame}

import argonaut._, Argonaut._

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService(implicit val blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, blackjackDeckService: BlackjackDeckService) {
  def bet(userId: Int, blackjackDeckId: Int, amount: BigDecimal): Option[BlackjackGame] = {
    blackjackGameDao.insertBlackjackGame(BlackjackGame(-1, userId, blackjackDeckId, -1)) match {
      case Some(game) =>
        // Create initial state for the game
        // TODO: Figure out what is status code, it's not really well defined
        val userHand = blackjackDeckService.getUserHands(blackjackDeckId, None).asJson.toString
        val dealerHand = blackjackDeckService.getDealerHand(blackjackDeckId, None).asJson.toString

        val description = "Some description for initial state"
        val blackjackGameState = BlackjackGameState(-1, userId, game.id, userHand, dealerHand, description, CommandService.BLACKJACK_BET, 0, -1)
        blackjackGameStateDao.insertBlackjackGameState(blackjackGameState)
        Option(game)
      // TODO: Send email report
      case None => throw new Exception("Couldn't create new blackjack game")
    }
  }

  def `user cards`(gameId: Int): Option[BlackjackHands] = {
    blackjackGameDao.findBlackjackGame(gameId) match {
      case Some(game) =>
        val blackjackGameState = blackjackGameStateDao.findLastBlackjackGameState(gameId)
        Option(blackjackDeckService.getUserHands(game.blackjackDeckId, blackjackGameState))
      case None => None
    }
  }

  def `dealer cards`(gameId: Int) = {
    blackjackGameDao.findBlackjackGame(gameId) match {
      case Some(game) =>
        val blackjackGameState = blackjackGameStateDao.findLastBlackjackGameState(gameId)
        Option(blackjackDeckService.getDealerHand(game.blackjackDeckId, blackjackGameState))
      case None => None
    }
  }

  def hit(gameId: Int): Option[BlackjackHands] = {
    // Make sure that User can hit
    val gameState = blackjackGameStateDao.findLastBlackjackGameState(gameId) match {
      case Some(state) => state
      case None => throw new Exception("Can't find state for the game")
    }
    // TODO: All exceptions that are not regular should be logged somewhere
    if (!canHit(gameState)) throw new CantHitException

    // Find game
    val game = blackjackGameDao.findBlackjackGame(gameId) match {
      case Some(blackjackGame) => blackjackGame
      case None => throw new Exception("Couldn't find the game")
    }

    // Get next game state and insert it in the database
    val nextGameState = blackjackDeckService.getNextState(game.blackjackDeckId, gameState, CommandService.BLACKJACK_HIT)
    blackjackGameStateDao.insertBlackjackGameState(nextGameState)

    // Return user's hands
    // TODO: Don't use Option once getUserHands(blackjackId, gameState) is implemented
    blackjackDeckService.getUserHands(game.blackjackDeckId, Option(gameState))
  }

  def `double-down`(gameId: Int) = ???

  def stand(gameId: Int) = ???

  def split(gameId: Int) = ???

  def insurance(gameId: Int) = ???

  def canHit(gameState: BlackjackGameState): Boolean = {
    ???
  }
}
