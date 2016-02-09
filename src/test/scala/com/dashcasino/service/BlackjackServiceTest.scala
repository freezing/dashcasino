package com.dashcasino.service

import com.dashcasino.DashUnitTest
import com.dashcasino.exception.{CantSplitException, CantDoubleDownException, NotEnoughMoneyException}
import com.dashcasino.model._
import com.dashcasino.service.account.ExternalDeposit
import com.dashcasino.service.blackjack._

/**
  * Created by freezing on 2/4/16.
  */
class BlackjackServiceTest extends DashUnitTest {
  val SIGNED = true

  "Blackjack Service" should "make sure start state is good" in {
    // Register user
    val user = userService.registerUser(User(-1, "blackjackservicetest_startstate@gmail.com", "testpass123", -1))

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
    userHands.head.outcome should be (BlackjackHandOutcome.PENDING)
    userHands.last.outcome should be (BlackjackHandOutcome.PENDING)
    // Dealer's state is DEALER
    startState.dealerHand.status should be (BlackjackHandStatus.DEALER)

    // - Dealer hand: {2, -1}
    startState.dealerHand.cardCodes should be (List(2, 0))

    startState.commandCode should be (commandService.blackjackBet.code)
    // Game status should be BLACKJACK_ROUND_RUNNING
    startState.statusCode should be (statusService.blackjackRoundRunning.code)
  }
  it should "test if split works - user WIN-BUSTED" in {
    val user = userService.registerUser(User(-1, "blackjackservicetest_split_win_busted@gmail.com", "testpass123", -1))

    // Deposit some money into account
    val depositMoney = BigDecimal(100.0)
    accountService.externalDeposit(ExternalDeposit(user.id, depositMoney, "{Description: Lets play some blackjack}"))

    val deck = createNewDeckSplit
    val betAmount = BigDecimal(10.0)
    val gameId = (blackjackService bet BlackjackBet(user.id, deck.id, betAmount)).gameId
    accountDao.findAccount(user.id).get.amount should be (BigDecimal(90.0))
    val splitState = blackjackService split BlackjackSplit(user.id, gameId)

    // Check that user has 10 and 11
    splitState.userHand.hands.head.cardCodes should be (List(10))
    splitState.userHand.hands.last.cardCodes should be (List(11))
    // Check that statuses are open
    splitState.userHand.hands.head.status should be (BlackjackHandStatus.OPEN)
    splitState.userHand.hands.last.status should be (BlackjackHandStatus.OPEN)
    // Check command and status
    splitState.commandCode should be (commandService.blackjackSplit.code)
    splitState.statusCode should be (statusService.blackjackRoundRunning.code)

    // Check that account has 80 in it (two bets)
    accountDao.findAccount(user.id).get.amount should be (BigDecimal(80.0))

    // Hit once then stand
    blackjackService hit BlackjackHit(user.id, gameId)
    val firstStandState = blackjackService stand BlackjackStand(user.id, gameId)
    // Check that first hand is standing and has {10, 12}
    firstStandState.userHand.hands.head.cardCodes should be (List(10, 12))
    firstStandState.userHand.hands.last.cardCodes should be (List(11))
    // Check statuses
    firstStandState.userHand.hands.head.status should be (BlackjackHandStatus.STANDING)
    firstStandState.userHand.hands.last.status should be (BlackjackHandStatus.OPEN)
    // Check command
    firstStandState.commandCode should be (commandService.blackjackStand.code)

    // Hit once
    blackjackService hit BlackjackHit(user.id, gameId)
    val secondState = blackjackService hit BlackjackHit(user.id, gameId)
    // Check that first hand is standing and has {10, 12}, while second hand is busted and has {11, 3, 9}
    secondState.userHand.hands.head.cardCodes should be (List(10, 12))
    secondState.userHand.hands.last.cardCodes should be (List(11, 3, 9))
    // Check statuses
    secondState.userHand.hands.head.status should be (BlackjackHandStatus.STANDING)
    secondState.userHand.hands.last.status should be (BlackjackHandStatus.BUSTED)
    // Check command
    secondState.commandCode should be (commandService.blackjackHit.code)

    // Dealer's hand should be completed
    secondState.dealerHand.cardCodes should be (List(13, 8))

    // Check winners
    secondState.userHand.hands.head.outcome should be (BlackjackHandOutcome.WON)
    secondState.userHand.hands.last.outcome should be (BlackjackHandOutcome.LOST)

    // Check game state to be round finished
    secondState.statusCode should be (statusService.blackjackRoundFinished.code)

    // Check account balance
    accountDao.findAccount(user.id).get.amount should be (BigDecimal(100.0))
  }
  it should "test if split works - user WIN-WIN" in {
    val user = userService.registerUser(User(-1, "blackjackservicetest_split_win_win@gmail.com", "testpass123", -1))

    // Deposit some money into account
    val depositMoney = BigDecimal(100.0)
    accountService.externalDeposit(ExternalDeposit(user.id, depositMoney, "{Description: Lets play some blackjack}"))

    val deck = createNewDeckSplitWinWin
    val betAmount = BigDecimal(10.0)
    val gameId = (blackjackService bet BlackjackBet(user.id, deck.id, betAmount)).gameId
    accountDao.findAccount(user.id).get.amount should be (BigDecimal(90.0))
    val splitState = blackjackService split BlackjackSplit(user.id, gameId)

    // Check that user has 10 and 11
    splitState.userHand.hands.head.cardCodes should be (List(10))
    splitState.userHand.hands.last.cardCodes should be (List(11))
    // Check that statuses are open
    splitState.userHand.hands.head.status should be (BlackjackHandStatus.OPEN)
    splitState.userHand.hands.last.status should be (BlackjackHandStatus.OPEN)
    // Check command and status
    splitState.commandCode should be (commandService.blackjackSplit.code)
    splitState.statusCode should be (statusService.blackjackRoundRunning.code)

    // Check that account has 80 in it (two bets)
    accountDao.findAccount(user.id).get.amount should be (BigDecimal(80.0))

    // Hit once then stand
    blackjackService hit BlackjackHit(user.id, gameId)
    val firstStandState = blackjackService stand BlackjackStand(user.id, gameId)
    // Check that first hand is standing and has {10, 12}
    firstStandState.userHand.hands.head.cardCodes should be (List(10, 12))
    firstStandState.userHand.hands.last.cardCodes should be (List(11))
    // Check statuses
    firstStandState.userHand.hands.head.status should be (BlackjackHandStatus.STANDING)
    firstStandState.userHand.hands.last.status should be (BlackjackHandStatus.OPEN)
    // Check command
    firstStandState.commandCode should be (commandService.blackjackStand.code)

    // Hit once
    blackjackService hit BlackjackHit(user.id, gameId)
    val secondState = blackjackService stand BlackjackStand(user.id, gameId)
    // Check that first hand is standing and has {10, 12}, while second hand is busted and has {11, 3, 9}
    secondState.userHand.hands.head.cardCodes should be (List(10, 12))
    secondState.userHand.hands.last.cardCodes should be (List(11, 3))
    // Check statuses
    secondState.userHand.hands.head.status should be (BlackjackHandStatus.STANDING)
    secondState.userHand.hands.last.status should be (BlackjackHandStatus.STANDING)
    // Check command
    secondState.commandCode should be (commandService.blackjackStand.code)

    // Dealer's hand should be completed
    secondState.dealerHand.cardCodes should be (List(13, 6, 9))

    // Check winners
    secondState.userHand.hands.head.outcome should be (BlackjackHandOutcome.WON)
    secondState.userHand.hands.last.outcome should be (BlackjackHandOutcome.WON)

    // Check game state to be round finished
    secondState.statusCode should be (statusService.blackjackRoundFinished.code)

    // Check account balance
    accountDao.findAccount(user.id).get.amount should be (BigDecimal(120.0))
  }
  it should "test if blackjack win works" in {
    val user = userService.registerUser(User(-1, "blackjackservicetest_userwinsafteronehit@gmail.com", "testpass123123", -1))
    accountService.externalDeposit(ExternalDeposit(user.id, BigDecimal(10.0), "Wanna play some BJ!!!"))
    val deck = createNewDeckUserWinsBlackjack

    val betAmount = BigDecimal(10.0)
    val finalState = blackjackService bet BlackjackBet(user.id, deck.id, betAmount)
    val gameId = finalState.gameId

    // Check that round is over
    finalState.statusCode should be (statusService.blackjackRoundFinished.code)
    // Check that user has WON
    finalState.userHand.hands.head.status should be (BlackjackHandStatus.BLACKJACK)
    finalState.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    finalState.userHand.hands.head.outcome should be (BlackjackHandOutcome.WON_BLACKJACK)
    finalState.userHand.hands.last.outcome should be (BlackjackHandOutcome.PENDING)

    // Check user's and dealer's cards
    finalState.userHand.hands.head.cardCodes should be (List(1, 10))
    finalState.userHand.hands.last.cardCodes should be (List())
    finalState.dealerHand.cardCodes should be (List(2, 0))

    // Check that player has doubled his account balance
    accountDao.findAccount(user.id).get.amount should be (BigDecimal(25.0))
  }
  it should "test if dealer and user blackjack is a tie" in {
    val user = userService.registerUser(User(-1, "blackjackservicetest_blackjacktie@gmail.com", "testpass123123", -1))
    accountService.externalDeposit(ExternalDeposit(user.id, BigDecimal(10.0), "Wanna play some BJ!!!"))
    val deck = createNewDeckBothBlackjacks

    val betAmount = BigDecimal(10.0)
    val finalState = blackjackService bet BlackjackBet(user.id, deck.id, betAmount)

    // Check that round is over
    finalState.statusCode should be (statusService.blackjackRoundFinished.code)
    // Check that user has WON
    finalState.userHand.hands.head.status should be (BlackjackHandStatus.BLACKJACK)
    finalState.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    finalState.userHand.hands.head.outcome should be (BlackjackHandOutcome.TIE)
    finalState.userHand.hands.last.outcome should be (BlackjackHandOutcome.PENDING)

    // Check user's and dealer's cards
    finalState.userHand.hands.head.cardCodes should be (List(1, 10))
    finalState.userHand.hands.last.cardCodes should be (List())
    finalState.dealerHand.cardCodes should be (List(11, 14))

    // Check that player has doubled his account balance
    accountDao.findAccount(user.id).get.amount should be (BigDecimal(10.0))
  }
  it should "test no duobledown allowed after hit" in {
    intercept[CantDoubleDownException] {
      val user = userService.registerUser(User(-1, "blackjackservicetest_nodoubledownafterhit@gmail.com", "testpass123123", -1))
      accountService.externalDeposit(ExternalDeposit(user.id, BigDecimal(20.0), "Wanna play some BJ!!!"))
      val deck = createNewDeckUserWins

      val betAmount = BigDecimal(10.0)
      val startState = blackjackService bet BlackjackBet(user.id, deck.id, betAmount)
      val gameId = startState.gameId

      blackjackService hit BlackjackHit(user.id, gameId)
      blackjackService doubleDown BlackjackDoubleDown(user.id, gameId)
    }
  }
  it should "test if doubledown win works" in {
    val user = userService.registerUser(User(-1, "blackjackservicetest_userwinsafterdoubledown@gmail.com", "testpass123123", -1))
    accountService.externalDeposit(ExternalDeposit(user.id, BigDecimal(20.0), "Wanna play some BJ!!!"))
    val deck = createNewDeckUserWins

    val betAmount = BigDecimal(10.0)
    val startState = blackjackService bet BlackjackBet(user.id, deck.id, betAmount)
    val gameId = startState.gameId

    val finalState = blackjackService doubleDown BlackjackDoubleDown(user.id, gameId)

    // Check that round is over
    finalState.statusCode should be (statusService.blackjackRoundFinished.code)
    // Check that user has WON
    finalState.userHand.hands.head.status should be (BlackjackHandStatus.DOUBLE_DOWN)
    finalState.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    finalState.userHand.hands.head.outcome should be (BlackjackHandOutcome.WON)
    finalState.userHand.hands.last.outcome should be (BlackjackHandOutcome.PENDING)

    // Check user's and dealer's cards
    finalState.userHand.hands.head.cardCodes should be (List(1, 8, 2))
    finalState.userHand.hands.last.cardCodes should be (List())
    finalState.dealerHand.cardCodes should be (List(4, 3, 5, 6))

    // Check that player has doubled his account balance
    accountDao.findAccount(user.id).get.amount should be (4 * betAmount)
  }
  it should "test if user earns money after he wins" in {
    val user = userService.registerUser(User(-1, "blackjackservicetest_userearnsmoneyafterwin@gmail.com", "testpass123123", -1))
    accountService.externalDeposit(ExternalDeposit(user.id, BigDecimal(10.0), "Wanna play some BJ!!!"))
    val deck = createNewDeckUserWins

    val betAmount = BigDecimal(10.0)
    val startState = blackjackService bet BlackjackBet(user.id, deck.id, betAmount)
    val gameId = startState.gameId

    blackjackService hit BlackjackHit(user.id, gameId)
    val finalState = blackjackService stand BlackjackStand(user.id, gameId)

    // Check that round is over
    finalState.statusCode should be (statusService.blackjackRoundFinished.code)
    // Check that user has WON
    finalState.userHand.hands.head.status should be (BlackjackHandStatus.STANDING)
    finalState.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    finalState.userHand.hands.head.outcome should be (BlackjackHandOutcome.WON)
    finalState.userHand.hands.last.outcome should be (BlackjackHandOutcome.PENDING)

    // Check user's and dealer's cards
    finalState.userHand.hands.head.cardCodes should be (List(1, 8, 2))
    finalState.userHand.hands.last.cardCodes should be (List())
    finalState.dealerHand.cardCodes should be (List(4, 3, 5, 6))

    // Check that player has doubled his account balance
    accountDao.findAccount(user.id).get.amount should be (2 * betAmount)
  }
  it should "work perfectly for this long sanity test" in {
    // Register user
    val user = userService.registerUser(User(-1, "blackjackservicetest_longsanity@gmail.com", "testpass123", -1))

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
    userHands.head.outcome should be (BlackjackHandOutcome.PENDING)
    userHands.last.outcome should be (BlackjackHandOutcome.PENDING)
    // Dealer's state is DEALER
    startState.dealerHand.status should be (BlackjackHandStatus.DEALER)

    // - Dealer hand: {2, -1}
    startState.dealerHand.cards map { card => card.code } should be (List(2, 0))

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
    state2.userHand.hands.head.outcome should be (BlackjackHandOutcome.PENDING)
    state2.userHand.hands.last.outcome should be (BlackjackHandOutcome.PENDING)
    // Game status should be BLACKJACK_ROUND_RUNNING
    state2.statusCode should be (statusService.blackjackRoundRunning.code)

    val state3 = blackjackService hit BlackjackHit(user.id, gameId)
    // Check that user has cards {1, 3, 5, 6}, {} and first one is open
    state3.userHand.hands.head.cardCodes should be (List(1, 3, 5, 6))
    state3.userHand.hands.last.cardCodes should be (List())
    state3.userHand.hands.head.status should be (BlackjackHandStatus.OPEN)
    state3.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    state3.userHand.hands.head.outcome should be (BlackjackHandOutcome.PENDING)
    state3.userHand.hands.last.outcome should be (BlackjackHandOutcome.PENDING)
    // Game status should be BLACKJACK_ROUND_RUNNING
    state3.statusCode should be (statusService.blackjackRoundRunning.code)

    // After next HIT user should be BUSTED
    val state4 = blackjackService hit BlackjackHit(user.id, gameId)
    // Check that user has cards {1, 3, 5, 6, 7}, {}, first one is BUSTED, second one is EMPTY
    state4.userHand.hands.head.cardCodes should be (List(1, 3, 5, 6, 7))
    state4.userHand.hands.last.cardCodes should be (List())
    state4.userHand.hands.head.status should be (BlackjackHandStatus.BUSTED)
    state4.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)
    state4.userHand.hands.head.outcome should be (BlackjackHandOutcome.LOST)
    state4.userHand.hands.last.outcome should be (BlackjackHandOutcome.PENDING)
    // Game status should be BLACKJACK_ROUND_FINISHED
    state4.statusCode should be (statusService.blackjackRoundFinished.code)

    // Dealer shouldn't even show his card since user BUSTED
    state4.dealerHand.cardCodes should be (List(2, 0))

    // Check that user has lost his money (i.e. nothing was deposited back)
    val newAccountState = accountDao.findAccount(user.id).get
    newAccountState.amount should be (BigDecimal(90.0))
  }
  it should "check that dealer wins after player stands and dealer draws 17" in {
    // Register user
    val user = userService.registerUser(User(-1, "blackjackservicetest_firststand@gmail.com", "testpass123", -1))

    // Deposit some money into account
    accountService.externalDeposit(ExternalDeposit(user.id, 100.0, "{Description: Lets play some blackjack}"))

    val deck = createNewDeck
    val betAmount = BigDecimal(10.0)
    val startState = blackjackService bet BlackjackBet(user.id, deck.id, betAmount)

    val gameId = startState.gameId

    // User has {1, 3}, Dealer has {-1, 4} where -1 is actually hidden 2
    // STAND
    val finalState = blackjackService.stand(BlackjackStand(user.id, gameId))
    // Make sure that user has the same cards
    finalState.userHand.hands.head.cardCodes should be (List(1, 3))
    finalState.userHand.hands.last.cardCodes should be (List())
    // And that his hand statu is STANDING
    finalState.userHand.hands.head.status should be (BlackjackHandStatus.STANDING)
    finalState.userHand.hands.last.status should be (BlackjackHandStatus.EMPTY)

    // Check dealer's hand - dealer should have drawn until >= 17, which in this case he should have
    // {2, 4, 5, 6}
    finalState.dealerHand.cardCodes should be (List(2, 4, 5, 6))
    finalState.commandCode should be (commandService.blackjackStand.code)
    // Check that the game has finished
    finalState.statusCode should be (statusService.blackjackRoundFinished.code)
    // Check that user has LOST
    finalState.userHand.hands.head.outcome should be (BlackjackHandOutcome.LOST)

    val account = accountDao.findAccount(user.id).get
    account.amount should be (BigDecimal(90.0))
  }
  it should "throw an exception NotEnoughMoneyException if user has no money for BET" in {
    intercept[NotEnoughMoneyException] {
      val user = userService.registerUser(User(-1, "blackjackservicetest_betnomoney@gmail.com", "testpass123", -1))
      val deck = createNewDeck
      blackjackService bet BlackjackBet(user.id, deck.id, 10.0)
    }
  }
  it should "throw an exception NotEnoughMoneyException if user has no money for DOUBLEDOWN" in {
    intercept[NotEnoughMoneyException] {
      val user = userService.registerUser(User(-1, "blacjackservicetest_doubledownnomoney@gmail.com", "testpass123", -1))
      val deck = createNewDeck
      // Add some money
      accountService.externalDeposit(ExternalDeposit(user.id, BigDecimal(10.0), ""))
      // Bet
      val gameState = blackjackService bet BlackjackBet(user.id, deck.id, BigDecimal(7.0))
      // Try to DoubleDown
      blackjackService doubleDown BlackjackDoubleDown(user.id, gameState.gameId)
    }
  }
  it should "throw an exception if user can't split and tries to" in {
    intercept[CantSplitException] {
      val user = userService.registerUser(User(-1, "blackjackservicetest_cantspliteception@gmail.com", "testpass123", -1))
      val deck = createNewDeck
      accountService.externalDeposit(ExternalDeposit(user.id, BigDecimal(10.0), ""))
      val gameState = blackjackService bet BlackjackBet(user.id, deck.id, BigDecimal(4.0))
      blackjackService split BlackjackSplit(user.id, gameState.gameId)
    }
  }
  it should "throw an exception if user has no money to split" in {
    intercept[NotEnoughMoneyException] {
      val user = userService.registerUser(User(-1, "blackjackservicetest_cantsplitnomoney@gmail.com", "testpass123", -1))
      val deck = createNewDeckSplit
      accountService.externalDeposit(ExternalDeposit(user.id, BigDecimal(10.0), ""))
      val gameState = blackjackService bet BlackjackBet(user.id, deck.id, BigDecimal(7.0))
      blackjackService split BlackjackSplit(user.id, gameState.gameId)
    }
  }

  def createNewDeck = blackjackDeckDao.insertBlackjackDeck(BlackjackDeck(-1, BlackjackDeckOrder((1 to 52).toList), "serverseed", "clientseed", SIGNED, -1)).get

  def createNewDeckUserWins = blackjackDeckDao.insertBlackjackDeck(BlackjackDeck(-1,
    BlackjackDeckOrder(
      List(1, 4, 8, 3, 2, 5, 6, 7) union (9 to 52).toList
    ), "serverseed", "clientseed", SIGNED, -1)).get

  def createNewDeckUserWinsBlackjack = blackjackDeckDao.insertBlackjackDeck(BlackjackDeck(-1,
    BlackjackDeckOrder(
      List(1, 2, 10, 4, 3, 5, 6, 7, 8, 9) union (11 to 52).toList
    ), "serverseed", "clientseed", SIGNED, -1)).get

  def createNewDeckBothBlackjacks = blackjackDeckDao.insertBlackjackDeck(BlackjackDeck(-1,
    BlackjackDeckOrder(
      List(1, 11, 10, 14, 2, 4, 3, 5, 6, 7, 8, 9, 12, 13) union (15 to 52).toList
    ), "serverseed", "clientseed", SIGNED, -1)).get

  def createNewDeckSplit = blackjackDeckDao.insertBlackjackDeck(BlackjackDeck(-1,
    BlackjackDeckOrder(
      List(10, 13, 11, 8, 12, 3, 9, 1, 2, 4, 5, 6,  7) union (14 to 52).toList
    ), "serverseed", "clientseed", SIGNED, -1)).get

  def createNewDeckSplitWinWin = blackjackDeckDao.insertBlackjackDeck(BlackjackDeck(-1,
    BlackjackDeckOrder(
      List(10, 13, 11, 6, 12, 3, 9, 1, 2, 4, 5, 8, 7) union (14 to 52).toList
    ), "serverseed", "clientseed", SIGNED, -1)).get

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
