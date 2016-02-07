package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.{CantSplitException, CantHitException}
import com.dashcasino.model._
import com.dashcasino.service.account.{InternalWithdrawal, AccountService}
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import com.dashcasino.service.blackjack.{BlackjackSplit, BlackjackHit, BlackjackService, BlackjackDeckService}
import com.dashcasino.service.{StatusCodeService, CommandService}

import argonaut._
import Argonaut._
import com.sun.javafx.binding.BindingHelperObserver
import sun.plugin.dom.exception.InvalidStateException

trait BlackjackSplitCommand { self: BlackjackServiceActor =>
  def split(msg: BlackjackSplit)
         (implicit blackjackGameDao: BlackjackGameSqlDao, accountService: AccountService, blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao, commandService: CommandService, statusCodeService: StatusCodeService): BlackjackGameState = {
    val (userId, gameId) = BlackjackSplit.unapply(msg).get
    val game = blackjackGameDao.findBlackjackGame(gameId).get

    checkAuthorization(userId, game)

    // Make sure that User can hit
    val gameState = blackjackGameStateDao.findLastBlackjackGameState(gameId).get
    if (!canSplit(gameState)) throw new CantSplitException

    // Withdraw money internally for SPLIT
    val reason = "{description: SPLIT}"
    accountService.internalWithdrawal(InternalWithdrawal(userId, currentUserHand(gameState).money, reason))

    // Get next game state and insert it in the database
    val nextGameState = getNextState(game.blackjackDeckId, gameState, commandService.blackjackSplit)
    blackjackGameStateDao.insertBlackjackGameState(nextGameState)
    nextGameState
  }

  // TODO: Check if user can have only two hands
  def canSplit(gameState: BlackjackGameState): Boolean = {
    canSplit(gameState.userHand.hands.head) && gameState.userHand.hands.last.status == BlackjackHandStatus.EMPTY
  }

  def canSplit(hand: BlackjackHand): Boolean = hand.status match {
    case BlackjackHandStatus.OPEN => canSplit(hand.cards)
    case BlackjackHandStatus.BUSTED => false
    case BlackjackHandStatus.DOUBLE_DOWN => false
    case BlackjackHandStatus.STANDING => false
    case unknown => throw new InvalidStateException(s"Invalid blackjack hand state: ${hand.status}")
  }

  def canSplit(cards: List[BlackjackCard]): Boolean = cards match {
    case first :: second :: Nil if first.primaryValue == second.primaryValue => true
    case _ => false
  }
}
