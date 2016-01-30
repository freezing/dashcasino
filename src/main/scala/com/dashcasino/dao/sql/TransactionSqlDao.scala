package com.dashcasino.dao.sql

import com.dashcasino.models.Transaction

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
trait TransactionSqlDao { self: SqlDao =>
  def toTransaction(rr: WrappedResultSet) = Transaction(rr.int("Id"), rr.int("AccountId"), rr.bigDecimal("Amount"), rr.int("CommandId"), rr.string("Reason"), rr.time("Timestamp").getTime)

  def insertTransaction(transaction: Transaction) = sql"INSERT INTO Transaction (AccountId, Amount, CommandId, Reason, Timestamp) VALUES (${transaction.accountId}, ${transaction.amount}, ${transaction.commandId}, ${transaction.reason}, CURRENT_TIMESTAMP)".update().apply()

  /**
    * @param accountId
    * @return transactions for the given accountId ordered by time ascending.
    */
  def findTransactions(accountId: Int) = sql"SELECT * FROM Transaction WHERE AccountId=$accountId ORDER BY Id ASC".map(toTransaction).list().apply()
}
