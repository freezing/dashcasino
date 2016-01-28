package com.dashcasino.services.dao.sql

import com.dashcasino.models.User
import com.dashcasino.services.dao.{DaoStatusCode, ResultCode, SqlDao}
import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
trait UserSqlDao { self: SqlDao =>
  def insertUser(user: User): ResultCode = {
    try {
      sql"INSERT INTO User (Email, PasswordHash, Timestamp) VALUES (${user.email}, ${user.passwordHash}, CURRENT_TIMESTAMP)".update().apply()
      ResultCode(DaoStatusCode.OK, "Success")
    } catch {
      case e: Exception => ResultCode(DaoStatusCode.ERROR, e.getLocalizedMessage)
    }
  }

  def findUser(email: String): Option[User] = {
    sql"SELECT * FROM User WHERE email=$email".map( rr =>
      User(rr.int("Id"), rr.string("Email"), rr.string("PasswordHash"), rr.time("Timestamp").getTime)
    ).single().apply()
  }

  def findUser(id: Int): Option[User] = {
    sql"SELECT * FROM User WHERE id=$id".map( rr =>
      User(rr.int("Id"), rr.string("Email"), rr.string("PasswordHash"), rr.time("Timestamp").getTime)
    ).single().apply()
  }
}
