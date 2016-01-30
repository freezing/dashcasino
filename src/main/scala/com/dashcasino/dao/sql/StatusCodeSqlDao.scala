package com.dashcasino.dao.sql

import com.dashcasino.models.StatusCode

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class StatusCodeSqlDao(implicit val session: AutoSession.type) {
  def toStatusCode(rr: WrappedResultSet) = StatusCode(rr.int("Id"), rr.string("Value"))

  def insertStatusCode(statusCode: StatusCode) = sql"INSERT INTO StatusCode (Value) VALUES (${statusCode.value})".update().apply()

  def findStatusCode(id: Int): Option[StatusCode] = sql"SELECT * FROM Command WHERE Id=$id".map(toStatusCode).single().apply()

  def findStatusCode(value: String): Option[StatusCode] = sql"SELECT * FROM Command WHERE Value=$value".map(toStatusCode).single().apply()
}
