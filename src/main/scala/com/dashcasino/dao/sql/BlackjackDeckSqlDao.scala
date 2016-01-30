package com.dashcasino.dao.sql

import com.dashcasino.model.{BlackjackDeck, BlackjackGame}
import scalikejdbc._

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackDeckSqlDao(implicit val session: AutoSession.type) {
  def toBlackjackDeck(rr: WrappedResultSet) = BlackjackDeck(rr.int("Id"), rr.string("order"), rr.string("ServerSeed"), rr.string("ClientSeed"))

  def insertBlackjackDeck(blackjackDeck: BlackjackDeck): Option[BlackjackDeck] = {
    DB localTx { implicit session =>
      sql"INSERT INTO BlackjackDeck (Order, ServerSeed, ClientSeed) VALUES (${blackjackDeck.order}, ${blackjackDeck.serverSeed}, ${blackjackDeck.clientSeed})".update().apply()
      sql"SELECT * FROM BlackjackDeck ORDER BY Id DESC LIMIT 1".map(toBlackjackDeck).single().apply()
    }
  }

  def findBlackjackDeck(id: Int): Option[BlackjackDeck] = {
    sql"SELECT * FROM BlackjackDeck WHERE Id=$id".map(toBlackjackDeck).single().apply()
  }
}
