package com.dashcasino.service

import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.dao.sql.{BlackjackGameSqlDao, BlackjackGameStateSqlDao}
import com.dashcasino.exception.{AuthorizationException, CantHitException}
import com.dashcasino.model._

import argonaut._, Argonaut._
import com.dashcasino.service.blackjack.commands.{BlackjackHitCommand, BlackjackGetCardsCommands, BlackjackBetCommand}

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService(implicit val blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, blackjackDeckService: BlackjackDeckService)
    extends BlackjackBetCommand with BlackjackGetCardsCommands with BlackjackHitCommand {
  def `double-down`(gameId: Int) = ???

  def stand(gameId: Int) = ???

  def split(gameId: Int) = ???

  def insurance(gameId: Int) = ???

  def checkAuthorization(userId: Int, game: BlackjackGame): Unit = {
    if (userId != game.userId) throw new AuthorizationException(s"User $userId has no authorization over game: $game")
  }
}
