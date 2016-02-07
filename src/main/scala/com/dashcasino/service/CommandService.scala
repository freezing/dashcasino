package com.dashcasino.service

import com.dashcasino.dao.sql.CommandSqlDao

/**
  * Created by freezing on 1/30/16.
  */
class CommandService(implicit val commandDao: CommandSqlDao) {
  val EXTERNAL_WITHDRAWAL = "EXTERNAL_WITHDRAWAL"
  val EXTERNAL_DEPOSIT = "EXTERNAL_DEPOSIT"
  val BLACKJACK_BET = "BLACKJACK_BET"
  val BLACKJACK_HIT = "BLACKJACK_HIT"
  val BLACKJACK_STAND = "BLACKJACK_STAND"
  val BLACKJACK_DOUBLEDOWN = "BLACKJACK_DOUBLEDOWN"
  val BLACKJACK_SPLIT = "BLACKJACK_SPLIT"

  initialize()

  def command(name: String) = commandDao.findCommand(name) match {
    case Some(command) => command
    case None => throw new Exception(s"Unknown command: $name")
  }

  private def initialize(): Unit = {
    // TODO: Read CSV and populate database if command with code doesn't exist
    // This is already done in DAO for both commands and statuses but it should be done in services instead
  }

  def blackjackBet = command(BLACKJACK_BET)
  def blackjackHit = command(BLACKJACK_HIT)
  def blackjackStand = command(BLACKJACK_STAND)
  def blackjackDoubleDown = command(BLACKJACK_DOUBLEDOWN)
  def blackjackSplit = command(BLACKJACK_SPLIT)

  def externalWithdrawal = command(EXTERNAL_WITHDRAWAL)
  def externalDeposit = command(EXTERNAL_DEPOSIT)
}

object CommandService extends CommandService
