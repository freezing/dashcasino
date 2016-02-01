package com.dashcasino.dao.sql

import com.dashcasino.model.{BlackjackGameState, BlackjackGame, Transaction}

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class BlackjackGameStateSqlDao(implicit val session: AutoSession.type) {
  def toBlackjackGameState(rr: WrappedResultSet) = BlackjackGameState(rr.int("Id"), rr.int("GameId"), rr.string("UserHand"), rr.string("DealerHand"), rr.string("Description"), rr.int("CommandCode"), rr.int("StatusCodeId"), rr.time("Timestamp").getTime)

  def insertBlackjackGameState(blackjackGameState: BlackjackGameState) =
    sql"INSERT INTO BlackjackGameState (GameId, UserHand, DealerHand, Description, CommandCode, StatusCodeId, Timestamp) VALUES (${blackjackGameState.gameId}, ${blackjackGameState.userHand}, ${blackjackGameState.dealerHand}, ${blackjackGameState.description}, ${blackjackGameState.commandCode}, ${blackjackGameState.statusCodeId}, CURRENT_TIMESTAMP)".update().apply()

  def findLastBlackjackGameState(gameId: Int) = sql"SELECT * FROM BlackjackGameState WHERE GameId=$gameId ORDER BY id DESC LIMIT 1".map(toBlackjackGameState).single().apply()
}
