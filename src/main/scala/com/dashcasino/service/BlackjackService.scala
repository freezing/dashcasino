package com.dashcasino.service

import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.model.{BlackjackDeck, BlackjackGameState, BlackjackGame}

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService(implicit val blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, blackjackDeckService: BlackjackDeckService) {
  def bet(userId: Int, amount: BigDecimal, blackjackDeck: BlackjackDeck): Option[BlackjackGame] = {
    blackjackGameDao.insertBlackjackGame(BlackjackGame(-1, userId, -1)) match {
      case Some(game) =>
        // Create initial state for the game
        // TODO: Figure out what is status code, it's not really well defined
        val userHand = blackjackDeckService.getUserHandsAsJson(blackjackDeck, None)
        val dealerHand = blackjackDeckService.getDealerHandAsJson(blackjackDeck, None)

        val description = "Some description for initial state"
        val blackjackGameState = BlackjackGameState(-1, userId, game.id, userHand, dealerHand, description, commandService.BLACKJACK_BET, 0, -1)
        blackjackGameStateDao.insertBlackjackGameState(blackjackGameState)
        Option(game)
      // TODO: Send email report
      case None => None
    }
  }

  def `my cards`(gameId: Int) = ???

  def `dealer cards`(gameId: Int) = ???

  def hit(gameId: Int) = ???

  def `double-down`(gameId: Int) = ???

  def stand(gameId: Int) = ???

  def split(gameId: Int) = ???

  def insurance(gameId: Int) = ???
}
