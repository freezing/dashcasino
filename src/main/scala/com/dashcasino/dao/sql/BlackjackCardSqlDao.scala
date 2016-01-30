package com.dashcasino.dao.sql

import com.dashcasino.models.{BlackjackCard, Command}

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
trait BlackjackCardSqlDao { self: SqlDao =>
  def toBlackjackCard(rr: WrappedResultSet) = BlackjackCard(rr.int("Id"), rr.int("Code"), rr.int("RankCode"), rr.string("RankName"), rr.string("RankLetter"), rr.string("SuitName"), rr.string("SuitLetter"), rr.int("SuitCode"), rr.int("PrimaryValue"), rr.int("SecondaryValue"))

  def insertBlackjackCard(card: BlackjackCard) = sql"INSERT INTO BlackjackCard (Code, RankCode, RankName, RankLetter, SuitName, SuitLetter, SuitCode, PrimaryValue, SecondaryValue) VALUES (${card.rankCode}, ${card.rankName}, ${card.rankLetter}, ${card.suitName}, ${card.suitCode}, ${card.primaryValue}, ${card.secondaryValue})".update().apply()

  def findBlackjackCard(rankLetter: String, suitLetter: String) = sql"SELECT * FROM BlackjackCard WHERE RankLetter=$rankLetter AND SuitLetter=$suitLetter".map(toBlackjackCard).single().apply()

  def unshuffledBlackjackCards() = sql"SELECT * FROM BlackjackCard ORDER BY Id".map(toBlackjackCard).list().apply()
}
