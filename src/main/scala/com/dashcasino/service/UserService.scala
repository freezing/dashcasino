package com.dashcasino.service

import com.dashcasino.model.User
import com.dashcasino.dao.sql.UserSqlDao
import com.dashcasino.service.account.AccountService

import com.github.t3hnar.bcrypt._

/**
  * Created by freezing on 1/27/16.
  */
class UserService(implicit userDao: UserSqlDao, accountService: AccountService) {
  // User registration needs WalletService to be able to generate depositAddress

  def registerUser(user: User): User = {
    val registeredUser = userDao.registerUser(user.copy(passwordHash = user.passwordHash.bcrypt))
    accountService.createAccount(registeredUser)
    registeredUser
  }

  def checkCredentials(user: User): Boolean = {
    userDao.findUser(user.email) match {
      case Some(u) => user.passwordHash isBcrypted u.passwordHash
      case None => false
    }
  }

  def isLogged(user: User): Boolean = {
    // TODO: Implement a method that checks if user is logged in or not
    ???
  }
}
