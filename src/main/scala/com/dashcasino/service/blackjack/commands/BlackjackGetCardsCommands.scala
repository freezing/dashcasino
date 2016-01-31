package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.BlackjackGameStateSqlDao
import com.dashcasino.model.BlackjackHands
import com.dashcasino.service.{BlackjackDeckService, BlackjackService}

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackGetCardsCommands { self: BlackjackService =>
  // TODO: Check if it is user's game
  def `user cards`(userId: Int, gameId: Int)
      (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao): Option[BlackjackHands] = {
    blackjackGameDao.findBlackjackGame(gameId) match {
      case Some(game) =>
        val blackjackGameState = blackjackGameStateDao.findLastBlackjackGameState(gameId)
        Option(blackjackDeckService.getUserHands(game.blackjackDeckId, blackjackGameState))
      case None => None
    }
  }

  // TODO: Check if it is user's game
  def `dealer cards`(userId: Int, gameId: Int)
                    (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao) = {
    blackjackGameDao.findBlackjackGame(gameId) match {
      case Some(game) =>
        val blackjackGameState = blackjackGameStateDao.findLastBlackjackGameState(gameId)
        Option(blackjackDeckService.getDealerHand(game.blackjackDeckId, blackjackGameState))
      case None => None
    }
  }
}
