package com.dashcasino.service

import com.dashcasino.model.User
import com.dashcasino.dao.sql.UserSqlDao
import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.service.wallet.WalletService
import scalikejdbc.DB

/**
  * Created by freezing on 1/27/16.
  */
class UserService(implicit dao: UserSqlDao, walletService: WalletService) {
  // User registration needs WalletService to be able to generate depositAddress

  def registerUser(user: User): ResultCode = {
    try {
      dao.registerUser(user, walletService.generateDepositAddress)
      ResultCode(DaoStatusCode.OK, "Registration successful")
    } catch {
      // TODO: Wallet should be able to throw exception if it fails to generate new address and it should be caught here
      case e: Exception => ResultCode(DaoStatusCode.ERROR, s"User with email: ${user.email} already exists!")
    }
  }

  def loginUser(user: User): ResultCode = {
    dao.findUser(user.email) match {
      case Some(_) => ResultCode(DaoStatusCode.OK, "User credentials match!")
      case None => ResultCode(DaoStatusCode.ERROR, "User credentials are invalid!")
    }
  }

  def isLogged(user: User): Boolean = {
    // TODO: Implement a method that checks if user is logged in or not
    ???
  }
}
