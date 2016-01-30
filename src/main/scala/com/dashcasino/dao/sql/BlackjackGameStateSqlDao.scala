package com.dashcasino.dao.sql

import com.dashcasino.models.{BlackjackGameState, BlackjackGame, Transaction}

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
trait BlackjackGameStateSqlDao { self: SqlDao =>
  def toBlackjackGameState(rr: WrappedResultSet) = BlackjackGameState(rr.int("Id"), rr.int("UserId"), rr.int("GameId"), rr.string("UserHand"), rr.string("DealerHand"), rr.string("Description"), rr.int("LastCommandId"), rr.int("StatusCodeId"), rr.time("Timestamp").getTime)

  def insertBlackjackGameState(blackjackGameState: BlackjackGameState) =
    sql"INSERT INTO BlackjackGameState (UserId, GameId, UserHand, DealerHand, Description, LastCommandId, StatusCodeId, Timestamp) VALUES (${blackjackGameState.userId}, ${blackjackGameState.gameId}, ${blackjackGameState.userHand}, ${blackjackGameState.dealerHand}, ${blackjackGameState.description}, ${blackjackGameState.commandId}, ${blackjackGameState.statusCodeId}, CURRENT_TIMESTAMP)".update().apply()

  def findLastBlackjackGameState(gameId: Int) = sql"SELECT * FROM BlackjackGameState WHERE GameId=$gameId ORDER BY id DESC LIMIT 1".map(toBlackjackGameState).single().apply()
}
