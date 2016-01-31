package com.dashcasino.service

import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.dao.sql.{BlackjackDeckSqlDao, BlackjackGameSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.{IllegalRequestException, AuthorizationException, CantHitException}
import com.dashcasino.model._

import argonaut._, Argonaut._
import com.dashcasino.service.blackjack.commands.{BlackjackHitCommand, BlackjackGetCardsCommands, BlackjackBetCommand}
import com.dashcasino.service.blackjack.logic.BlackjackStateTransition

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService(implicit val blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, blackjackDeckService: BlackjackDeckService)
    extends BlackjackBetCommand with BlackjackGetCardsCommands with BlackjackHitCommand with BlackjackStateTransition{
  def `double-down`(gameId: Int) = ???

  def stand(gameId: Int) = ???

  def split(gameId: Int) = ???

  def insurance(gameId: Int) = ???

  def checkAuthorization(userId: Int, game: BlackjackGame): Unit = {
    if (userId != game.userId) throw new AuthorizationException(s"User $userId has no authorization over game: $game")
  }

  def getDeck(deckId: Int)(implicit blackjackDeckSqlDao: BlackjackDeckSqlDao): BlackjackDeck =
    blackjackDeckSqlDao.findBlackjackDeck(deckId) match {
      case Some(x) => x
      // TODO: Send email report
      case None => throw new IllegalArgumentException(s"Invalid blackjack deck id: $deckId")
    }

  def decodeDealerHand(json: String): BlackjackHand = json.decodeOption[BlackjackHand] match {
    case Some(x) => x
    // TODO: Send email report
    case None => throw new IllegalStateException(s"Invalid blackjack dealer hand json: $json")
  }

  def decodeUserHands(json: String): BlackjackHands = json.decodeOption[BlackjackHands] match {
    case Some(x) => x
    // TODO: Send email report
    case None => throw new IllegalStateException(s"Invalid blackjack user hands json: $json")
  }

  def getGame(gameId: Int): BlackjackGame = blackjackGameDao.findBlackjackGame(gameId) match {
    case Some(game) => game
    // TODO: Send email report
    case None => throw new IllegalRequestException(s"Invalid game id: $gameId")
  }
}
