package com.dashcasino.service

import com.dashcasino.DashUnitTest
import com.dashcasino.exception.NotEnoughMoneyException
import com.dashcasino.model.{BlackjackDeckOrder, BlackjackDeck, User}
import com.dashcasino.service.account.ExternalDeposit
import com.dashcasino.service.blackjack.BlackjackBet

/**
  * Created by freezing on 2/4/16.
  */
class BlackjackServiceTest extends DashUnitTest {
  val SIGNED = true

  "Blackjack Service" should "work perfectly for this long sanity test" in {
    // Register user
    val user = userService.registerUser(User(-1, "blackjackservicetest1@gmail.com", "testpass123", -1))
    val account = accountDao.findAccount(user.id)

    // Deposit some money into account
    accountService.externalDeposit(ExternalDeposit(user.id, 100.0, "{Description: Lets play some blackjack}"))

    val deck = createNewDeck
    blackjackService bet BlackjackBet(user.id, deck.id, 10.0)
    1 should be (1)
  }
  it should "throw an exception NotEnoughMoneyException if use has no money for BET" in {
    intercept[NotEnoughMoneyException] {
      val user = userService.registerUser(User(-1, "blackjackservicetest2@gmail.com", "testpass123", -1))
      val deck = createNewDeck
      blackjackService bet BlackjackBet(user.id, deck.id, 10.0)
    }
  }

  def createNewDeck = blackjackDeckDao.insertBlackjackDeck(BlackjackDeck(-1, BlackjackDeckOrder((1 to 52).toList), "serverseed", "clientseed", SIGNED, -1)).get
}
