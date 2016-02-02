package com.dashcasino.dao

import com.dashcasino.DashUnitTest
import org.scalatest._

/**
  * Created by freezing on 2/2/16.
  */

// TODO: CHANGE LATER TO BE UNIT TESTS
class CommandSqlDaoTest extends DashUnitTest {
  val commands = Map(
    0 -> "EXTERNAL_WITHDRAWAL",
    1 -> "EXTERNAL_DEPOSIT",
    2 -> "BLACKJACK_BET",
    3 -> "BLACKJACK_HIT",
    4 -> "BLACKJACK_STAND",
    5 -> "BLACKJACK_DOUBLEDOWN"
  )

  "Command SQL Dao" should "return command by name with corresponding code" in {
    commands foreach { case (k, v) =>
      val cmd = commandDao.findCommand(v).get
      cmd.code should be(k)
      cmd.name should be(v)
    }
  }
}
