package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackDeckSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.{CantDoubleDownException, CantHitException}
import com.dashcasino.model.{BlackjackHandStatus, BlackjackHand, BlackjackHands, BlackjackGameState}
import com.dashcasino.service.account.{InternalWithdrawal, AccountService}
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import com.dashcasino.service.blackjack.{BlackjackDoubleDown, BlackjackService, BlackjackDeckService}
import com.dashcasino.service.{StatusCodeService, CommandService}
import sun.plugin.dom.exception.InvalidStateException

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
    if (!canDoubleDown(gameState)) throw new CantDoubleDownException

    // Internal withdrawal for double-down
    accountService.internalWithdrawal(InternalWithdrawal(userId, currentUserHand(gameState).money, "{description: DOUBLEDOWN}"))

    // Get next game state and insert it in the database
    val nextGameState = getNextState(game.blackjackDeckId, gameState, commandService.blackjackDoubleDown)
    blackjackGameStateDao.insertBlackjackGameState(nextGameState)
    processPayment(userId, nextGameState)
  }

  def canDoubleDown(gameState: BlackjackGameState): Boolean = {
    canDoubleDown(gameState.userHand.hands.head) && gameState.userHand.hands.last.status == BlackjackHandStatus.EMPTY
  }

  def canDoubleDown(hand: BlackjackHand): Boolean = hand.status match {
    case BlackjackHandStatus.OPEN => if (hand.cards.length == 2) true else false
    case BlackjackHandStatus.BUSTED => false
    case BlackjackHandStatus.DOUBLE_DOWN => false
    case BlackjackHandStatus.STANDING => false
    case unknown => throw new InvalidStateException(s"Invalid blackjack hand state: ${hand.status}")
  }
}
