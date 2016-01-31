package com.dashcasino.service

import com.dashcasino.dao.sql.CommandSqlDao

/**
  * Created by freezing on 1/30/16.
  */
class CommandService(implicit val commandDao: CommandSqlDao) {
  // TODO: Implement command service - think about this
  val EXTERNAL_WITHDRAWAL = 0
  val EXTERNAL_DEPOSIT = 1
  val BLACKJACK_BET = 2
  val BLACKJACK_HIT = 3
  val BLACKJACK_STAND = 4
  val BLACKJACK_DOUBLEDOWN = 5
}

object CommandService extends CommandService
