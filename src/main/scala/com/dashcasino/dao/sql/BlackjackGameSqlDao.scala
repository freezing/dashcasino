package com.dashcasino.dao.sql

import com.dashcasino.model.{BlackjackGame, Transaction}

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class BlackjackGameSqlDao(implicit val session: AutoSession.type) {
  def toBlackjackGame(rr: WrappedResultSet) = BlackjackGame(rr.int("Id"), rr.int("UserId"), rr.time("Timestamp").getTime)

  def insertBlackjackGame(blackjackGame: BlackjackGame) = sql"INSERT INTO BlackjackGame (UserId, Timestamp) VALUES (${blackjackGame.userId}, CURRENT_TIMESTAMP)".update().apply()

  // No need for find yet
}
