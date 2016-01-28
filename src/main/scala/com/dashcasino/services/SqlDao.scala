package com.dashcasino.services

import scalikejdbc._

import com.dashcasino.models._

/**
  * Created by freezing on 1/28/16.
  */
// TODO: Read credentials from config file and enable LIVE and TEST modes
trait SqlDao extends SqlUser {
  // initialize JDBC driver & connection pool
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://33.33.33.10:3306/dashcasino", "root", "root")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession


}


trait SqlUser { self: SqlDao =>
  def insertUser(user: User): ResultCode = {
    val code = sql"INSERT INTO User (Email, PasswordHash, Timestamp) VALUES (${user.email}, ${user.passwordHash}, CURRENT_TIMESTAMP)".update().apply()
    ResultCode(code, "No idea")
  }
}

object Test extends App with SqlDao {
  val user = User(-1, "nikolavla@gmail.com", "passHash123aosdalskdj1l", -1)
  insertUser(user)
  println(insertUser(user))
  println(insertUser(user))
}