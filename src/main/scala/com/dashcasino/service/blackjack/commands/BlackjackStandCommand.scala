package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.{CantStandException, CantHitException}
import com.dashcasino.model.{BlackjackHandStatus, BlackjackHand, BlackjackHands, BlackjackGameState}
import com.dashcasino.service.{StatusCodeService, CommandService}
import com.dashcasino.service.blackjack.{BlackjackStand, BlackjackService}
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import sun.plugin.dom.exception.InvalidStateException

import argonaut._
import Argonaut._

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStandCommand { self: BlackjackServiceActor =>
  def stand(msg: BlackjackStand)(implicit blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, commandService: CommandService, statusCodeService: StatusCodeService) = {
    val (userId, gameId) = BlackjackStand.unapply(msg).get

    val game = blackjackGameDao.findBlackjackGame(gameId).get

    checkAuthorization(userId, game)

    // Make sure that User can hit
    val gameState = blackjackGameStateDao.findLastBlackjackGameState(gameId).get
    if (!canStand(gameState)) throw new CantStandException

    // Get next game state and insert it in the database
    val nextGameState = getNextState(game.blackjackDeckId, gameState, commandService.blackjackStand)
    blackjackGameStateDao.insertBlackjackGameState(nextGameState)

    // Return user's hands
    nextGameState.userHand
  }

  // TODO: REFACTOR SINCE CAN STAND AND CAN HIT HAVE THE SAME LOGIC
  def canStand(gameState: BlackjackGameState): Boolean = {
    gameState.userHand.hands exists { hand => canStand(hand) }
  }

  def canStand(hand: BlackjackHand): Boolean = hand.status match {
    case BlackjackHandStatus.OPEN => true
    case BlackjackHandStatus.BUSTED => false
    case BlackjackHandStatus.DOUBLE_DOWN => false
    case BlackjackHandStatus.STANDING => false
    case unknown => throw new InvalidStateException(s"Invalid blackjack hand state: ${hand.status}")
  }
}
