package com.dashcasino

/**
  * Created by freezing on 1/28/16.
  */
package object services {
  def hash(plain: String): String = {
    // TODO: Implement a hash function (Use library ofc :D)
    plain
  }

  // Implicit services
  implicit val walletService = new WalletService
  implicit val userService = new UserService
}
