package com.dashcasino.dao.sql

import com.dashcasino.model._

import scalikejdbc._

import argonaut._, Argonaut._

/**
  * Created by freezing on 1/28/16.
  */
class BlackjackGameStateSqlDao(implicit val session: AutoSession.type) {
  def toBlackjackGameState(rr: WrappedResultSet) = BlackjackGameState(rr.int("Id"), rr.int("GameId"),
    rr.string("UserHand").decodeOption[BlackjackHands].get, rr.string("DealerHand").decodeOption[BlackjackHand].get,
    rr.string("Description"), rr.int("CommandCode"), rr.int("StatusCodeId"), rr.int("Insurance"), rr.time("Timestamp").getTime)

  def insertBlackjackGameState(blackjackGameState: BlackjackGameState) =
    sql"INSERT INTO BlackjackGameState (GameId, UserHand, DealerHand, Description, CommandCode, StatusCode, Insurance, Timestamp) VALUES (${blackjackGameState.gameId}, ${blackjackGameState.userHand.asJson.nospaces}, ${blackjackGameState.dealerHand.asJson.nospaces}, ${blackjackGameState.description}, ${blackjackGameState.commandCode}, ${blackjackGameState.statusCode}, ${blackjackGameState.insurance}, CURRENT_TIMESTAMP)".update().apply()

  def findLastBlackjackGameState(gameId: Int) = sql"SELECT * FROM BlackjackGameState WHERE GameId=$gameId ORDER BY id DESC LIMIT 1".map(toBlackjackGameState).single().apply()
}
