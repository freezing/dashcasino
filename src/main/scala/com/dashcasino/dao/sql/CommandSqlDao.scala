package com.dashcasino.dao.sql

import com.dashcasino.models.Command

import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class CommandSqlDao(implicit val session: AutoSession.type) {
  def toCommand(rr: WrappedResultSet) = Command(rr.int("Id"), rr.string("Name"))

  def insertCommand(command: Command) = sql"INSERT INTO Command (Name) VALUES (${command.name})".update().apply()

  def findCommand(id: Int): Option[Command] = sql"SELECT * FROM Command WHERE Id=$id".map(toCommand).single().apply()

  def findCommand(name: String): Option[Command] = sql"SELECT * FROM Command WHERE Name=$name".map(toCommand).single().apply()
}
