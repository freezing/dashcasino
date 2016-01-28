package com.dashcasino.services.dao.sql

import com.dashcasino.models.{BlackjackGame, Transaction}

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
trait BlackjackGameSqlDao { self: SqlDao =>
  def toBlackjackGame(rr: WrappedResultSet) = BlackjackGame(rr.int("Id"), rr.int("UserId"), rr.time("Timestamp").getTime)

  def insertBlackjackGame(blackjackGame: BlackjackGame) = sql"INSERT INTO BlackjackGame (UserId, Timestamp) VALUES (${blackjackGame.userId}, CURRENT_TIMESTAMP)".update().apply()

  // No need for find yet
}
