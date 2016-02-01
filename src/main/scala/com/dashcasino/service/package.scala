package com.dashcasino

import com.dashcasino.service.account.AccountService
import com.dashcasino.service.blackjack.{BlackjackService, BlackjackDeckService}
import com.dashcasino.service.wallet.WalletService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by freezing on 1/28/16.
  */
package object service {
  def hash(plain: String): String = {
    // TODO: Implement a hash function (Use library ofc :D)
    plain
  }

  // Implicit services
  implicit val commandService = new CommandService
  implicit val walletService = new WalletService
  implicit val userService = new UserService
  implicit val accountService = new AccountService
  implicit val blackjackDeckService = new BlackjackDeckService
  implicit val blackjackService = new BlackjackService
}
