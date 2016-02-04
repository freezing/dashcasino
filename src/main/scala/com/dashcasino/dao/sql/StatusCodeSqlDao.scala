package com.dashcasino.dao.sql

import com.dashcasino.model.{Command, StatusCode}

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class StatusCodeSqlDao(implicit val session: AutoSession.type) {
  init()

  def toStatusCode(rr: WrappedResultSet) = StatusCode(rr.int("Id"), rr.int("Code"), rr.string("Value"))

  def insertStatusCode(statusCode: StatusCode) = sql"INSERT INTO StatusCode (Code, Value) VALUES (${statusCode.code}, ${statusCode.value})".update().apply()

  def findStatusCode(code: Int): Option[StatusCode] = sql"SELECT * FROM StatusCode WHERE Code=$code".map(toStatusCode).single().apply()

  def findStatusCode(value: String): Option[StatusCode] = sql"SELECT * FROM StatusCode WHERE Value=$value".map(toStatusCode).single().apply()

  private def init(): Unit = {
    val filePath = getClass.getResource("statuses.csv")
    scala.io.Source.fromFile(filePath.getFile).getLines foreach { line =>
      val vals = line.split(",")
      val (code, name) = (vals(0).toInt, vals(1))
      val status = StatusCode(-1, code, name)
      findStatusCode(code) match {
        case None => insertStatusCode(status)
        case Some(_) =>
      }
    }
  }
}
