package com.dashcasino.service

import com.dashcasino.dao.sql.StatusCodeSqlDao

/**
  * Created by freezing on 2/4/16.
  */
class StatusCodeService(implicit val statusDao: StatusCodeSqlDao) {
  val UNKNOWN = "UNKNOWN"
  val BLACKJACK_GAME_RUNNING = "BLACKJACK_GAME_RUNNING"
  val BLACKJACK_GAME_FINISHED = "BLACKJACK_GAME_FINISHED"
  val BLACKJACK_ROUND_RUNNING = "BLACKJACK_ROUND_RUNNING"
  val BLACKJACK_ROUND_FINISHED = "BLACKJACK_ROUND_FINISHED"

  def status(value: String) = statusDao.findStatusCode(value) match {
    case Some(status) => status
    case None => throw new Exception(s"Unknown status: $value")
  }

  def unknown = status(UNKNOWN)
  def blackjackGameRunning = status(BLACKJACK_GAME_RUNNING)
  def blackjackGameFinished = status(BLACKJACK_GAME_FINISHED)
  def blackjackRoundRunning = status(BLACKJACK_ROUND_RUNNING)
  def blackjackRoundFinished = status(BLACKJACK_ROUND_FINISHED)

  def isGameFinished(statusCode: Int): Boolean = statusCode == blackjackGameFinished.code || statusCode == blackjackRoundFinished.code
}
