package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackDeckSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.CantHitException
import com.dashcasino.model.{BlackjackHands, BlackjackGameState}
import com.dashcasino.service.account.{InternalWithdrawal, AccountService}
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import com.dashcasino.service.blackjack.{BlackjackDoubleDown, BlackjackService, BlackjackDeckService}
import com.dashcasino.service.{StatusCodeService, CommandService}

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackDoubleDownCommand { self: BlackjackServiceActor =>
  def `double-down`(msg: BlackjackDoubleDown)
    (implicit accountService: AccountService, blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, commandService: CommandService, statusCodeService: StatusCodeService): BlackjackGameState = {
      val (userId, gameId) = BlackjackDoubleDown.unapply(msg).get
      val game = blackjackGameDao.findBlackjackGame(gameId).get

      checkAuthorization(userId, game)

      // Make sure that User can double down
      val gameState = blackjackGameStateDao.findLastBlackjackGameState(gameId).get
      // TODO: CantPlayException should be used everywhere
      if (!canDoubleDown(gameState)) throw new CantHitException

      // Internal withdrawal for double-down
      accountService.internalWithdrawal(InternalWithdrawal(userId, currentUserHand(gameState).money, "{description: DOUBLEDOWN}"))

      // Get next game state and insert it in the database
      val nextGameState = getNextState(game.blackjackDeckId, gameState, commandService.blackjackDoubleDown)
      blackjackGameStateDao.insertBlackjackGameState(nextGameState)

      // Return game state
    nextGameState
  }

  // TODO: TO USE DOUBLE DOWN USER MUST WITHDRAW SAME AMOUNT OF MONEY HE BET
  def canDoubleDown(state: BlackjackGameState): Boolean = canHit(state)
}
