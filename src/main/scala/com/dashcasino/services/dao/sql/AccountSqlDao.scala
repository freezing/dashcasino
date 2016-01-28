package com.dashcasino.services.dao.sql

import com.dashcasino.models.Account

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
trait AccountSqlDao { self: SqlDao =>
  def toAccount(rr: WrappedResultSet) = Account(rr.int("Id"), rr.int("UserId"), rr.string("DepositAddress"), rr.bigDecimal("Amount"))

  def insertAccount(account: Account) = sql"INSERT INTO Account (UserId, DepositAddress, Amount) VALUES (${account.userId}, ${account.depositAddress}, ${account.amount})".update().apply()

  def findAccount(userId: Int): Option[Account] = sql"SELECT * FROM User WHERE UserId=$userId".map(toAccount).single().apply()
}
