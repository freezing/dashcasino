package com.dashcasino.dao.sql

import com.dashcasino.model.{BlackjackCard, Command}

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class BlackjackCardSqlDao(implicit val session: AutoSession.type) {
  init()

  def toBlackjackCard(rr: WrappedResultSet) = BlackjackCard(rr.int("Id"), rr.int("Code"), rr.int("RankCode"), rr.string("RankName"), rr.string("RankLetter"), rr.string("SuitName"), rr.string("SuitLetter"), rr.int("SuitCode"), rr.int("PrimaryValue"), rr.int("SecondaryValue"))

  def insertBlackjackCard(card: BlackjackCard) = sql"INSERT INTO BlackjackCard (Code, RankCode, RankName, RankLetter, SuitName, SuitLetter, SuitCode, PrimaryValue, SecondaryValue) VALUES (${card.code}, ${card.rankCode}, ${card.rankName}, ${card.rankLetter}, ${card.suitName}, ${card.suitLetter}, ${card.suitCode}, ${card.primaryValue}, ${card.secondaryValue})".update().apply()

  def findBlackjackCard(rankLetter: String, suitLetter: String) = sql"SELECT * FROM BlackjackCard WHERE RankLetter=$rankLetter AND SuitLetter=$suitLetter".map(toBlackjackCard).single().apply()

  def findBlackjackCard(code: Int) = sql"SELECT * FROM BlackjackCard WHERE Code=$code".map(toBlackjackCard).single.apply.get

  def unshuffledBlackjackCards() = sql"SELECT * FROM BlackjackCard ORDER BY Id".map(toBlackjackCard).list.apply

  private def init(): Unit = {
    val filePath = getClass.getResource("blackjackcards.csv")
    scala.io.Source.fromFile(filePath.getFile).getLines foreach { line =>
      val vals = line.split(",") map { x => x.trim }
      val (code, rankCode, rankName, rankLetter, suitName, suitLetter, suitCode, primaryValue, secondaryValue) = {
        (vals(0).toInt,
          vals(1).toInt,
          vals(2),
          vals(3),
          vals(4),
          vals(5),
          vals(6).toInt,
          vals(7).toInt,
          vals(8).toInt)
      }
      val card = BlackjackCard(-1, code, rankCode, rankName, rankLetter, suitName, suitLetter, suitCode, primaryValue, secondaryValue)
      try {
        findBlackjackCard(code)
      } catch {
        case e: Exception => insertBlackjackCard(card)
      }
    }
  }
}