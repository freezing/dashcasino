package com.dashcasino.dao.sql

import com.dashcasino.models.{Account, User}
import com.dashcasino.services.{WalletService, UserService}
import scalikejdbc._

/**
  * Created by freezing on 1/28/16.
  */
class UserSqlDao(implicit accountSqlDao: AccountSqlDao, session: AutoSession.type) {
  def toUser(rr: WrappedResultSet) = User(rr.int("Id"), rr.string("Email"), rr.string("PasswordHash"), rr.time("Timestamp").getTime)

  def insertUser(user: User) = sql"INSERT INTO User (Email, PasswordHash, Timestamp) VALUES (${user.email}, ${user.passwordHash}, CURRENT_TIMESTAMP)".update().apply()

  def findUser(email: String): Option[User] = sql"SELECT * FROM User WHERE email=$email".map(toUser).single().apply()

  def findUser(id: Int): Option[User] = sql"SELECT * FROM User WHERE id=$id".map(toUser).single().apply()

  def registerUser(user: User, depositAddress: String) = {
    DB localTx { implicit session =>
      insertUser(user)
      findUser(user.email) match {
        case Some(u) => accountSqlDao.insertAccount(Account (- 1, u.id, depositAddress, 0.0))
        // TODO: Check if this should throw an exception or do something else
        case None => throw new Exception("Inserted user not found. Strange behaviour!!!")
      }
    }
  }
}

object Test extends App {
  import com.dashcasino.dao._
  implicit val walletService = new WalletService
  val userService = new UserService
  userService.registerUser(User(-1, "test@gmail.com", "asd123", -1))
}
