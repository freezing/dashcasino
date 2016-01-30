package com.dashcasino.service

import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackGameStateSqlDao}
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
        val blackjackGameState = BlackjackGameState(-1, userId, game.id, userHand, dealerHand, description, commandService.BLACKJACK_BET, 0, -1)
        blackjackGameStateDao.insertBlackjackGameState(blackjackGameState)
        Option(game)
      // TODO: Send email report
      case None => None
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

  def hit(gameId: Int) = ???

  def `double-down`(gameId: Int) = ???

  def stand(gameId: Int) = ???

  def split(gameId: Int) = ???

  def insurance(gameId: Int) = ???
}
