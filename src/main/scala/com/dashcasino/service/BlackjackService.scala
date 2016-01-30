package com.dashcasino.service

import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.{AuthorizationException, CantHitException}
import com.dashcasino.model._

import argonaut._, Argonaut._
import sun.plugin.dom.exception.InvalidStateException

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService(implicit val blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, blackjackDeckService: BlackjackDeckService) {
  def bet(userId: Int, blackjackDeckId: Int, amount: BigDecimal): Option[BlackjackGame] = {
    blackjackGameDao.insertBlackjackGame(BlackjackGame(-1, userId, blackjackDeckId, -1)) match {
      case Some(game) =>
        // Create initial state for the game
        // TODO: Figure out what is status code, it's not really well defined
        val userHand = blackjackDeckService.getUserHands(blackjackDeckId, None).asJson.spaces2
        val dealerHand = blackjackDeckService.getDealerHand(blackjackDeckId, None).asJson.spaces2

        val description = "Some description for initial state"
        val blackjackGameState = BlackjackGameState(-1, userId, game.id, userHand, dealerHand, description, CommandService.BLACKJACK_BET, 0, -1)
        blackjackGameStateDao.insertBlackjackGameState(blackjackGameState)
        Option(game)
      // TODO: Send email report
      case None => throw new Exception("Couldn't create new blackjack game")
    }
  }

  // TODO: Check if it is user's game
  def `user cards`(userId: Int, gameId: Int): Option[BlackjackHands] = {
    blackjackGameDao.findBlackjackGame(gameId) match {
      case Some(game) =>
        val blackjackGameState = blackjackGameStateDao.findLastBlackjackGameState(gameId)
        Option(blackjackDeckService.getUserHands(game.blackjackDeckId, blackjackGameState))
      case None => None
    }
  }

  // TODO: Check if it is user's game
  def `dealer cards`(userId: Int, gameId: Int) = {
    blackjackGameDao.findBlackjackGame(gameId) match {
      case Some(game) =>
        val blackjackGameState = blackjackGameStateDao.findLastBlackjackGameState(gameId)
        Option(blackjackDeckService.getDealerHand(game.blackjackDeckId, blackjackGameState))
      case None => None
    }
  }

  def hit(userId: Int, gameId: Int): BlackjackHands = {
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
    if (!canHit(gameState)) throw new CantHitException

    // Get next game state and insert it in the database
    val nextGameState = blackjackDeckService.getNextState(game.blackjackDeckId, gameState, CommandService.BLACKJACK_HIT)
    blackjackGameStateDao.insertBlackjackGameState(nextGameState)

    // Return user's hands
    // TODO: Don't use Option once getUserHands(blackjackId, gameState) is implemented
    blackjackDeckService.getUserHands(game.blackjackDeckId, Option(gameState))
  }

  def `double-down`(gameId: Int) = ???

  def stand(gameId: Int) = ???

  def split(gameId: Int) = ???

  def insurance(gameId: Int) = ???

  def canHit(hand: BlackjackHand): Boolean = hand.status match {
    case BlackjackHandStatus.OPEN => true
    case BlackjackHandStatus.BUSTED => false
    case BlackjackHandStatus.DOUBLE_DOWN => false
    case BlackjackHandStatus.STANDING => false
    case unknown => throw new InvalidStateException(s"Invalid blackjack hand state: ${hand.status}")
  }

  def canHit(gameState: BlackjackGameState): Boolean = {
    gameState.userHand.decodeOption[BlackjackHands] match {
      case Some(userHand) => userHand.hands exists { hand => canHit(hand) }
      // TODO: This should never happen, unless invalid JSON is inserted in database, in which case we should send email report and investigate
      case None => false
    }
  }

  def checkAuthorization(userId: Int, game: BlackjackGame): Unit = {
    if (userId != game.userId) throw new AuthorizationException(s"User $userId has no authorization over game: $game")
  }
}
