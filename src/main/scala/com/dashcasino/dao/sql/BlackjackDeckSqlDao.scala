package com.dashcasino.dao.sql

import com.dashcasino.model.{BlackjackDeckOrder, BlackjackDeck}
import scalikejdbc._

import argonaut._
import Argonaut._

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackDeckSqlDao(implicit val session: AutoSession.type) {
  def toBlackjackDeckOrder(json: String) = json.decodeOption[List[Int]] match {
    case Some(x) => BlackjackDeckOrder(x)
        // TODO: Send email report
    case None => throw new IllegalStateException(s"Invalid JSON object for BlackjackDeckOrder in database: $json")
  }
  def toBlackjackDeck(rr: WrappedResultSet) = BlackjackDeck(rr.int("Id"), toBlackjackDeckOrder(rr.string("order")), rr.long("ServerSeed"), rr.string("ClientSeed"), rr.boolean("IsSigned"), rr.time("Timestamp").getTime)

  def insertBlackjackDeck(blackjackDeck: BlackjackDeck): Option[BlackjackDeck] = {
    DB localTx { implicit session =>
      sql"INSERT INTO BlackjackDeck (`Order`, ServerSeed, ClientSeed, IsSigned, Timestamp) VALUES (${blackjackDeck.order.cards.asJson.nospaces}, ${blackjackDeck.serverSeed}, ${blackjackDeck.clientSeed}, ${blackjackDeck.isSigned}, CURRENT_TIMESTAMP)".update().apply()
      sql"SELECT * FROM BlackjackDeck ORDER BY Id DESC LIMIT 1".map(toBlackjackDeck).single().apply()
    }
  }

  def findBlackjackDeck(id: Int): Option[BlackjackDeck] = {
    sql"SELECT * FROM BlackjackDeck WHERE Id=$id".map(toBlackjackDeck).single().apply()
  }
}
