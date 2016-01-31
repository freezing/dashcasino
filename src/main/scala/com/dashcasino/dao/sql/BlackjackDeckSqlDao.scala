package com.dashcasino.dao.sql

import com.dashcasino.model.{BlackjackDeckOrder, BlackjackDeck, BlackjackGame}
import scalikejdbc._

import argonaut._
import Argonaut._

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackDeckSqlDao(implicit val session: AutoSession.type) {
  def toBlackjackDeckOrder(json: String) = json.decodeOption[BlackjackDeckOrder] match {
    case Some(x) => x
        // TODO: Send email report
    case None => throw new IllegalStateException(s"Invalid JSON object for BlackjackDeckOrder in database: $json")
  }
  def toBlackjackDeck(rr: WrappedResultSet) = BlackjackDeck(rr.int("Id"), toBlackjackDeckOrder(rr.string("order")), rr.string("ServerSeed"), rr.string("ClientSeed"))

  def insertBlackjackDeck(blackjackDeck: BlackjackDeck): Option[BlackjackDeck] = {
    DB localTx { implicit session =>
      sql"INSERT INTO BlackjackDeck (Order, ServerSeed, ClientSeed) VALUES (${blackjackDeck.order.asJson.spaces2}, ${blackjackDeck.serverSeed}, ${blackjackDeck.clientSeed})".update().apply()
      sql"SELECT * FROM BlackjackDeck ORDER BY Id DESC LIMIT 1".map(toBlackjackDeck).single().apply()
    }
  }

  def findBlackjackDeck(id: Int): Option[BlackjackDeck] = {
    sql"SELECT * FROM BlackjackDeck WHERE Id=$id".map(toBlackjackDeck).single().apply()
  }
}
