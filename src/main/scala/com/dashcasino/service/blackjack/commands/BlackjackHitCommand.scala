package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.BlackjackGameStateSqlDao
import com.dashcasino.exception.CantHitException
import com.dashcasino.model.{BlackjackHandStatus, BlackjackHand, BlackjackGameState, BlackjackHands}
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import com.dashcasino.service.blackjack.{BlackjackHit, BlackjackService, BlackjackDeckService}
import com.dashcasino.service.CommandService

import argonaut._
import Argonaut._
import sun.plugin.dom.exception.InvalidStateException

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackHitCommand { self: BlackjackServiceActor =>
  def hit(msg: BlackjackHit)
         (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao, commandService: CommandService): BlackjackHands = {
    val (userId, gameId) = BlackjackHit.unapply(msg).get
    val game = blackjackGameDao.findBlackjackGame(gameId).get

    checkAuthorization(userId, game)

    // Make sure that User can hit
    val gameState = blackjackGameStateDao.findLastBlackjackGameState(gameId).get
    if (!canHit(gameState)) throw new CantHitException

    // Get next game state and insert it in the database
    val nextGameState = getNextState(game.blackjackDeckId, gameState, commandService.blackjackHit)
    blackjackGameStateDao.insertBlackjackGameState(nextGameState)

    // Return user's hands
    userHands(nextGameState)
  }

  def canHit(gameState: BlackjackGameState): Boolean = {
    gameState.userHand.decodeOption[BlackjackHands] match {
      case Some(userHand) => userHand.hands exists { hand => canHit(hand) }
      // TODO: This should never happen, unless invalid JSON is inserted in database, in which case we should send email report and investigate
      case None => false
    }
  }

  def canHit(hand: BlackjackHand): Boolean = hand.status match {
    case BlackjackHandStatus.OPEN => true
    case BlackjackHandStatus.BUSTED => false
    case BlackjackHandStatus.DOUBLE_DOWN => false
    case BlackjackHandStatus.STANDING => false
    case unknown => throw new InvalidStateException(s"Invalid blackjack hand state: ${hand.status}")
  }
}
