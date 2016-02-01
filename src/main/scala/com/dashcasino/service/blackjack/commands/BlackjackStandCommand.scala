package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.BlackjackGameStateSqlDao
import com.dashcasino.exception.{CantStandException, CantHitException}
import com.dashcasino.model.{BlackjackHandStatus, BlackjackHand, BlackjackHands, BlackjackGameState}
import com.dashcasino.service.CommandService
import com.dashcasino.service.blackjack.BlackjackService
import sun.plugin.dom.exception.InvalidStateException

import argonaut._
import Argonaut._

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStandCommand { self: BlackjackService =>
  def stand(userId: Int, gameId: Int)(implicit blackjackGameStateDao: BlackjackGameStateSqlDao) = {
    // Find game
    val game = blackjackGameDao.findBlackjackGame(gameId) match {
      case Some(blackjackGame) => blackjackGame
      case None => throw new Exception("Couldn't find the game")
    }

    checkAuthorization(userId, game)

    // Make sure that User can hit
    val gameState = blackjackGameStateDao.findLastBlackjackGameState(gameId) match {
      case Some(state) => state
      case None => throw new Exception("Can't find state for the game")
    }
    // TODO: All exceptions that are not regular should be logged somewhere
    if (!canStand(gameState)) throw new CantStandException

    // Get next game state and insert it in the database
    val nextGameState = getNextState(game.blackjackDeckId, gameState, CommandService.BLACKJACK_STAND)
    blackjackGameStateDao.insertBlackjackGameState(nextGameState)

    // Return user's hands
    userHands(nextGameState)
  }

  // TODO: REFACTOR SINCE CAN STAND AND CAN HIT HAVE THE SAME LOGIC
  def canStand(gameState: BlackjackGameState): Boolean = {
    gameState.userHand.decodeOption[BlackjackHands] match {
      case Some(userHand) => userHand.hands exists { hand => canStand(hand) }
      // TODO: This should never happen, unless invalid JSON is inserted in database, in which case we should send email report and investigate
      case None => false
    }
  }

  def canStand(hand: BlackjackHand): Boolean = hand.status match {
    case BlackjackHandStatus.OPEN => true
    case BlackjackHandStatus.BUSTED => false
    case BlackjackHandStatus.DOUBLE_DOWN => false
    case BlackjackHandStatus.STANDING => false
    case unknown => throw new InvalidStateException(s"Invalid blackjack hand state: ${hand.status}")
  }
}
