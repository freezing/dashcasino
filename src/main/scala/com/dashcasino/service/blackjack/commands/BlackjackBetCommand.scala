package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.{BlackjackGameStateSqlDao, BlackjackGameSqlDao}
import com.dashcasino.model.BlackjackGame
import com.dashcasino.service.account.AccountService
import com.dashcasino.service.blackjack.{BlackjackService, BlackjackDeckService}
import com.dashcasino.service.CommandService

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackBetCommand { self: BlackjackService =>
  def bet(userId: Int, blackjackDeckId: Int, amount: BigDecimal)
         (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao,
          accountService: AccountService): BlackjackGame = {
    blackjackGameDao.insertBlackjackGame(BlackjackGame(-1, userId, blackjackDeckId, -1)) match {
      case Some(game) =>
        // Create initial state for the game
        val blackjackGameState = getInitialState(blackjackDeckId, game.id, amount)
        blackjackGameStateDao.insertBlackjackGameState(blackjackGameState)
        game
      // TODO: Send email report
      case None => throw new Exception("Couldn't create new blackjack game")
    }
  }
}
