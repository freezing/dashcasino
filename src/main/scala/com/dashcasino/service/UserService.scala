package com.dashcasino.service

import com.dashcasino.model.User
import com.dashcasino.dao.sql.UserSqlDao
import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.service.account.AccountService
import com.dashcasino.service.wallet.WalletService
import scalikejdbc.DB

/**
  * Created by freezing on 1/27/16.
  */
class UserService(implicit userDao: UserSqlDao, accountService: AccountService) {
  // User registration needs WalletService to be able to generate depositAddress

  def registerUser(user: User): Unit = {
    val registeredUser = userDao.registerUser(user).get
    accountService.createAccount(registeredUser)
  }

  def checkCredentials(user: User): Boolean = {
    userDao.findUser(user.email) match {
      case Some(_) => true
      case None => false
    }
  }

  def isLogged(user: User): Boolean = {
    // TODO: Implement a method that checks if user is logged in or not
    ???
  }
}
