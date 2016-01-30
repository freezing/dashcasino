package com.dashcasino.services.dao.sql

import com.dashcasino.models.User
import com.dashcasino.services.dao.{DaoStatusCode, ResultCode}
import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
trait UserSqlDao { self: SqlDao =>
  def toUser(rr: WrappedResultSet) = User(rr.int("Id"), rr.string("Email"), rr.string("PasswordHash"), rr.time("Timestamp").getTime)

  def insertUser(user: User) = sql"INSERT INTO User (Email, PasswordHash, Timestamp) VALUES (${user.email}, ${user.passwordHash}, CURRENT_TIMESTAMP)".update().apply()

  def findUser(email: String): Option[User] = sql"SELECT * FROM User WHERE email=$email".map(toUser).single().apply()

  def findUser(id: Int): Option[User] = sql"SELECT * FROM User WHERE id=$id".map(toUser).single().apply()
}