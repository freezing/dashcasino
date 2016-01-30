package com.dashcasino.services

import com.dashcasino.models.User
import com.dashcasino.dao.sql.SqlDao
import com.dashcasino.dao.{DaoStatusCode, ResultCode}

/**
  * Created by freezing on 1/27/16.
  */
trait UserService {
  def dao: SqlDao

  def registerUser(user: User): ResultCode = {
    try {
      dao.insertUser(user)
      // TODO: Get inserted User (need this for userId, maybe find a better solution later)
      // TODO: Create User Account
      ResultCode(DaoStatusCode.OK, "Registration successful")
    } catch {
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
