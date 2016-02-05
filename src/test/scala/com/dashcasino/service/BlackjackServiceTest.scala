package com.dashcasino.service

import com.dashcasino.DashUnitTest
import com.dashcasino.exception.NotEnoughMoneyException
import com.dashcasino.model._
import com.dashcasino.service.account.ExternalDeposit
import com.dashcasino.service.blackjack.{BlackjackHit, BlackjackBet}

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
    val betAmount = BigDecimal(10.0)
    val startState = blackjackService bet BlackjackBet(user.id, deck.id, betAmount)

    // Start state should:
    // - User hands: {1, 3}, {}
    val userHands = startState.userHand.hands
    // Only first hand is in play
    userHands.head.money should be (betAmount)
    userHands.last.money should be (BigDecimal(0.0))

    // Check hands cards, and first one is open, second one is empty
    userHands.head.cardCodes should be (List(1, 3))
    userHands.last.cardCodes should be (List())
    userHands.head.status should be (BlackjackHandStatus.OPEN)
    userHands.last.status should be (BlackjackHandStatus.EMPTY)
    // Dealer's state is DEALER
    startState.dealerHand.status should be (BlackjackHandStatus.DEALER)

    // - Dealer hand: {2, -1}
    startState.dealerHand.cards map { card => card.code } should be (List(0, 4))

    startState.commandCode should be (commandService.blackjackBet.code)
    // Game status should be BLACKJACK_ROUND_RUNNING
    startState.statusCode should be (statusService.blackjackRoundRunning.code)

    val gameId = startState.gameId

    // User has A, 3 = 14/4. Hit a couple of times and go for hard hand
    val state2 = blackjackService hit BlackjackHit(user.id, gameId)
    // Check that user has cards {1, 3, 5}, {} and first one is open
    state2.userHand.hands.head.cardCodes should be (List(1, 3, 5))
    state2.userHand.hands.last.cardCodes should be (List())
    state2.userHand.hands.head.status should be (BlackjackHandStatus.OPEN)
    state2.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    // Game status should be BLACKJACK_ROUND_RUNNING
    state2.statusCode should be (statusService.blackjackRoundRunning.code)

    val state3 = blackjackService hit BlackjackHit(user.id, gameId)
    // Check that user has cards {1, 3, 5, 6}, {} and first one is open
    state3.userHand.hands.head.cardCodes should be (List(1, 3, 5, 6))
    state3.userHand.hands.last.cardCodes should be (List())
    state3.userHand.hands.head.status should be (BlackjackHandStatus.OPEN)
    state3.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    // Game status should be BLACKJACK_ROUND_RUNNING
    state3.statusCode should be (statusService.blackjackRoundRunning.code)

    // After next HIT user should be BUSTED
    val state4 = blackjackService hit BlackjackHit(user.id, gameId)
    // Check that user has cards {1, 3, 5, 6, 7}, {}, first one is BUSTED, second one is EMPTY
    state4.userHand.hands.head.cardCodes should be (List(1, 3, 5, 6, 7))
    state4.userHand.hands.last.cardCodes should be (List())
    state4.userHand.hands.head.status should be (BlackjackHandStatus.BUSTED)
    state4.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    // Game status should be BLACKJACK_ROUND_FINISHED
    state4.statusCode should be (statusService.blackjackRoundFinished.code)

    // Dealer shouldn't even show his card since user BUSTED
    state4.dealerHand.cardCodes should be (List(0, 4))
  }
  it should "throw an exception NotEnoughMoneyException if use has no money for BET" in {
    intercept[NotEnoughMoneyException] {
      val user = userService.registerUser(User(-1, "blackjackservicetest2@gmail.com", "testpass123", -1))
      val deck = createNewDeck
      blackjackService bet BlackjackBet(user.id, deck.id, 10.0)
    }
  }

  def createNewDeck = blackjackDeckDao.insertBlackjackDeck(BlackjackDeck(-1, BlackjackDeckOrder((1 to 52).toList), "serverseed", "clientseed", SIGNED, -1)).get

  implicit class CardCodesEasyAccess(blackjackHand: BlackjackHand) {
    def cardCodes = blackjackHand.cards map { c => c.code }
  }

//  implicit class GetCardsByPositionsForDeck(blackjackDeck: BlackjackDeck) {
//    def cards(positions: Int*): List[Int] = {
//      (positions map { pos => if (pos == -1) 0 else blackjackDeck.order.cards(pos) }).toList
//    }
//
//    def cards: List[Int] = List.empty[Int]
//  }
}
