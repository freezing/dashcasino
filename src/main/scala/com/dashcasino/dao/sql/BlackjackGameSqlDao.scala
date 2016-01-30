package com.dashcasino.dao.sql

import com.dashcasino.model.{BlackjackGame, Transaction}

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class BlackjackGameSqlDao(implicit val session: AutoSession.type) {
  def toBlackjackGame(rr: WrappedResultSet) = BlackjackGame(rr.int("Id"), rr.int("UserId"), rr.time("Timestamp").getTime)

  /**
    * Inserts and returns inserted BlackjackGame
    * @param blackjackGame
    * @return
    */
  def insertBlackjackGame(blackjackGame: BlackjackGame): Option[BlackjackGame] = {
    DB localTx { implicit session =>
      sql"INSERT INTO BlackjackGame (UserId, Timestamp) VALUES (${blackjackGame.userId}, CURRENT_TIMESTAMP)".update().apply()
      sql"SELECT * FROM BlackjackGame ORDER BY Id LIMIT 1".map(toBlackjackGame).single().apply()
    }
  }

  // No need for find yet
}
