package com.dashcasino.dao.sql

import com.dashcasino.model.Transaction

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class TransactionSqlDao(implicit val session: AutoSession.type) {
  def toTransaction(rr: WrappedResultSet) = Transaction(rr.int("Id"), rr.int("AccountId"), rr.bigDecimal("Amount"), rr.string("Reason"), rr.int("Confirmed"), rr.time("Timestamp").getTime)

  def insertTransaction(transaction: Transaction) = sql"INSERT INTO Transaction (AccountId, Amount, Reason, Confirmed, Timestamp) VALUES (${transaction.accountId}, ${transaction.amount}, ${transaction.reason}, ${transaction.confirmed}, CURRENT_TIMESTAMP)".update().apply()

  /**
    * @param accountId
    * @return transactions for the given accountId ordered by time ascending.
    */
  def findTransactions(accountId: Int): List[Transaction] = sql"SELECT * FROM Transaction WHERE AccountId=$accountId ORDER BY Id ASC".map(toTransaction).list().apply()
}
