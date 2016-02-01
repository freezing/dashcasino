package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.{BlackjackGameStateSqlDao, BlackjackGameSqlDao}
import com.dashcasino.model.{BlackjackGameState, BlackjackGame}
import com.dashcasino.service.account.AccountService
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import com.dashcasino.service.blackjack.{BlackjackBet, BlackjackService, BlackjackDeckService}
import com.dashcasino.service.CommandService

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackBetCommand { self: BlackjackServiceActor =>
  def bet(msg: BlackjackBet)
         (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao,
          accountService: AccountService): BlackjackGameState = {
    val (userId, blackjackDeckId, amount) = BlackjackBet.unapply(msg).get
    // TODO: Maybe refactor this with throwing exception. It should be thrown on a lower level if something failed.
    blackjackGameDao.insertBlackjackGame(BlackjackGame(-1, userId, blackjackDeckId, -1)) match {
      case Some(game) =>
        // Create initial state for the game
        val blackjackGameState = getInitialState(blackjackDeckId, game.id, amount)
        blackjackGameStateDao.insertBlackjackGameState(blackjackGameState)
        blackjackGameState
      // TODO: Send email report
      case None => throw new Exception("Couldn't create new blackjack game")
    }
  }
}
