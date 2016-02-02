package com.dashcasino.service

import com.dashcasino.DashUnitTest
import com.dashcasino.model.User

/**
  * Created by freezing on 2/2/16.
  */
class UserServiceTest extends DashUnitTest {
  "UserService" should "register user successfully and create its account" in {
    val email = "nikolavla@gmail.com"
    val pass = "testpass"

    // Make sure user doesn't exist
    val user = userService.registerUser(User(-1, email, pass, -1))

    // User should exist in the database
    userDao.findUser(email) shouldNot be (None)

    // Credentials should work
    userService.checkCredentials(User(-1, email, pass, -1)) should be (true)

    // It should have account
    val account = accountDao.findAccount(user.id).get
    account.userId should be (user.id)
    account.amount should be (0.0)
    account.depositAddress.length should be > 0
  }
}
