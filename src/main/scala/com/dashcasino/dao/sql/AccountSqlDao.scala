package com.dashcasino.dao.sql

import com.dashcasino.model.Account

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class AccountSqlDao(implicit val session: AutoSession.type) {
  def toAccount(rr: WrappedResultSet) = Account(rr.int("Id"), rr.int("UserId"), rr.string("DepositAddress"), rr.bigDecimal("Amount"))

  def insertAccount(account: Account) = sql"INSERT INTO Account (UserId, DepositAddress, Amount) VALUES (${account.userId}, ${account.depositAddress}, ${account.amount})".update().apply()

  def findAccount(userId: Int): Option[Account] = sql"SELECT * FROM Account WHERE UserId=$userId".map(toAccount).single().apply()

  def updateMoney(id: Int, amount: BigDecimal) = {
    sql"UPDATE Account SET Amount=Amount+${amount.toDouble} WHERE Id=$id".update().apply()
  }
}
