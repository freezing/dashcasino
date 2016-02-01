package com.dashcasino.service.blackjack

import argonaut.Argonaut._
import argonaut._
import com.dashcasino.dao.sql.{BlackjackCardSqlDao, BlackjackDeckSqlDao, BlackjackGameSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.{AuthorizationException, IllegalRequestException}
import com.dashcasino.model._
import com.dashcasino.service.blackjack.commands.{BlackjackBetCommand, BlackjackGetCardsCommands, BlackjackHitCommand, BlackjackStandCommand}
import com.dashcasino.service.blackjack.logic.BlackjackStateTransition

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService(implicit val blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, blackjackCardDao: BlackjackCardSqlDao, blackjackDeckSqlDao: BlackjackDeckSqlDao)
    extends BlackjackBetCommand with BlackjackGetCardsCommands with BlackjackHitCommand with BlackjackStateTransition with BlackjackStandCommand {
  def split(gameId: Int) = ???

  def insurance(gameId: Int) = ???

  def checkAuthorization(userId: Int, game: BlackjackGame): Unit = {
    if (userId != game.userId) throw new AuthorizationException(s"User $userId has no authorization over game: $game")
  }

  def getCard(code: Int): BlackjackCard = blackjackCardDao.findBlackjackCard(code) match {
    case Some(x) => x
    case None => throw new IllegalRequestException(s"Invalid card code: $code")
  }

  def getDeck(deckId: Int): BlackjackDeck =
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
