package com.dashcasino.dao.sql

import com.dashcasino.model.Command

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class CommandSqlDao(implicit val session: AutoSession.type) {
  def toCommand(rr: WrappedResultSet) = Command(rr.int("Id"), rr.int("Code"), rr.string("Name"))

  def insertCommand(command: Command) = sql"INSERT INTO Command (Name, Code) VALUES (${command.name}, ${command.code})".update().apply()

  def findCommand(code: Int): Option[Command] = sql"SELECT * FROM Command WHERE Code=$code".map(toCommand).single().apply()

  def findCommand(name: String): Option[Command] = sql"SELECT * FROM Command WHERE Name=$name".map(toCommand).single().apply()
}
