package com.dashcasino

import com.dashcasino.service.account.AccountService
import com.dashcasino.service.blackjack.{BlackjackService, BlackjackDeckService}
import com.dashcasino.service.wallet.WalletService

/**
  * Created by freezing on 1/28/16.
  */
package object service {
  // TODO: Implicit services should only be created in the app, not here, since it is automatically injected in tests
//  // Implicit services
//  implicit lazy val commandService = new CommandService
//  implicit lazy val walletService = new WalletService
//  implicit lazy val accountService = new AccountService
//  implicit lazy val userService = new UserService
//  implicit lazy val blackjackDeckService = new BlackjackDeckService
//  implicit lazy val blackjackService = new BlackjackService
}
