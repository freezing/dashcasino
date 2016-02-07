package com.dashcasino.service.blackjack.logic.statetransition

import argonaut.Argonaut._
import argonaut._
import com.dashcasino.dao.sql.{BlackjackCardSqlDao, BlackjackDeckSqlDao}
import com.dashcasino.model._
import com.dashcasino.service.{StatusCodeService, CommandService}
import com.dashcasino.service.blackjack.BlackjackService
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import spray.util.NotImplementedException

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransition extends BlackjackStateTransitionSplit with BlackjackStateTransitionHit with BlackjackStateTransitionDealerDrawing with BlackjackStateTransitionStand with BlackjackStateTransitionDoubleDown { self: BlackjackServiceActor =>
  val NOT_PAID = false

  def hideIfNotBlackjack(hand: BlackjackHand)(implicit blackjackCardDao: BlackjackCardSqlDao): BlackjackHand = {
    isBlackjack(hand.cards) match {
      case true => hand.copy(status = BlackjackHandStatus.BLACKJACK)
        // First card stays hidden, second one is shown
      case false => hand.copy(cards = List(blackjackCardDao.findBlackjackCard(BlackjackCardCodes.FACE_DOWN), hand.cards(1)), status = BlackjackHandStatus.DEALER)
    }
  }

  def drawCard(hand: BlackjackHand, card: BlackjackCard): BlackjackHand = {
    val newCards = hand.cards ++ List(card)
    val status = getHandStatus(newCards)
    hand.copy(cards = newCards, status = status)
  }

  def getHandStatus(cards: List[BlackjackCard]) = {
    if (isBlackjack(cards)) BlackjackHandStatus.BLACKJACK
    else if (isOpen(cards)) BlackjackHandStatus.OPEN
    else if (isBusted(cards)) BlackjackHandStatus.BUSTED
    else BlackjackHandStatus.STANDING
  }

  // TODO: This is the same as above only OPEN != DEALER, Should be REFACTORED in a better way
  def getDealerHandStatus(cards: List[BlackjackCard]) = {
    getHandStatus(cards) match {
      case BlackjackHandStatus.OPEN => BlackjackHandStatus.DEALER
      case x => x
    }
  }

  // TODO: Create separate methods for CREATE_USER_HAND and CREATE_DEALER_HAND
  def createUserHand(firstCardCode: Int, secondCardCode: Int, money: BigDecimal)(implicit blackjackCardDao: BlackjackCardSqlDao): BlackjackHand = {
    val card1 = blackjackCardDao.findBlackjackCard(firstCardCode)
    val card2 = blackjackCardDao.findBlackjackCard(secondCardCode)
    val cards = List(card1, card2)
    BlackjackHand(cards, getHandStatus(cards), BlackjackHandOutcome.PENDING, money, NOT_PAID)
  }

  def createDealerHand(firstCardCode: Int, secondCardCode: Int, money: BigDecimal)(implicit blackjackCardDao: BlackjackCardSqlDao): BlackjackHand = {
    val card1 = blackjackCardDao.findBlackjackCard(firstCardCode)
    val card2 = blackjackCardDao.findBlackjackCard(secondCardCode)
    val cards = List(card1, card2)
    BlackjackHand(cards, getDealerHandStatus(cards), BlackjackHandOutcome.PENDING, money, NOT_PAID)
  }

  def createInitialUserHand(deck: BlackjackDeck, money: BigDecimal): BlackjackHand =
    createUserHand(deck.order.cards(0), deck.order.cards(2), money)

  def createInitialDealerHand(deck: BlackjackDeck, money: BigDecimal): BlackjackHand =
    hideIfNotBlackjack(createDealerHand(deck.order.cards(1), deck.order.cards(3), money))

  /**
    * Initial state is created by the following rules:
    * 1. Draw card and deal it to player face-up
    * 2. Draw card and deal it to dealer face-down
    * 3. Draw card and deal it to player face-up
    * 4. Draw card and deal it to dealer face-up
 *
    * @param blackjackDeckId Deck id used to retrieve deck configuration (i.e. order)
    * @return Initial BlackjackGameState after the BET is placed.
    */
  def getInitialState(blackjackDeckId: Int, gameId: Int, money: BigDecimal)(implicit blackjackDeckDao: BlackjackDeckSqlDao, blackjackCardDao: BlackjackCardSqlDao, commandService: CommandService, statusCodeService: StatusCodeService): BlackjackGameState = {
    val deck = getDeck(blackjackDeckId)

    // Create dealer hand
    val description = "Nothing for now"
    val dealerHand = createInitialDealerHand(deck, money)

    // User hand is created from cards at indices 0 and 2
    val firstUserHand = createInitialUserHand(deck, money)
    val userBlackjackHands = List(firstUserHand, BlackjackHand(List.empty[BlackjackCard], BlackjackHandStatus.EMPTY, BlackjackHandOutcome.PENDING, 0.0, NOT_PAID))
    val userHand = checkBlackjacks(BlackjackHands(userBlackjackHands), dealerHand)

    val statusCode = getGameStatus(userHand).code

    BlackjackGameState(-1, gameId, userHand, dealerHand, description, commandService.blackjackBet.code, statusCode, 0 /* no insurance at start */, -1)
  }

  def checkBlackjacks(userHands: BlackjackHands, dealerHand: BlackjackHand): BlackjackHands = {
    if (dealerHand.status == BlackjackHandStatus.BLACKJACK) {
      // If user has Blackjack, it's a TIE, otherwise user lost
      userHands.hands.head.status match {
        case BlackjackHandStatus.BLACKJACK => userInstaUpdate(userHands, BlackjackHandOutcome.TIE)
        case _ => userInstaUpdate(userHands, BlackjackHandOutcome.LOST)
      }
    } else {
      userHands.hands.head.status match {
        case BlackjackHandStatus.BLACKJACK => userInstaUpdate(userHands, BlackjackHandOutcome.WON_BLACKJACK)
        case _ => userHands
      }
    }
  }

  def userInstaUpdate(userHands: BlackjackHands, outcome: String): BlackjackHands = {
    BlackjackHands(List(userHands.hands.head.copy(outcome = outcome), userHands.hands.last))
  }

  def getNextState(blackjackDeckId: Int, blackjackGameState: BlackjackGameState, command: Command)
                  (implicit blackjackDeckDao: BlackjackDeckSqlDao, blackjackCardDao: BlackjackCardSqlDao, commandService: CommandService, statusCodeService: StatusCodeService): BlackjackGameState = {
    val deck = getDeck(blackjackDeckId)
    val dealerHand = blackjackGameState.dealerHand
    val userHands = blackjackGameState.userHand

    // Draw next card for the state
    val nextCard = deck.order.cards(dealerHand.cards.length + userHands.hands.head.cards.length + userHands.hands(1).cards.length)

    // TODO: Think of a way to refactor. This is not very nice
    command.name match {
      case CommandService.BLACKJACK_HIT => nextStateAfterHit(blackjackGameState, deck, nextCard)
      case CommandService.BLACKJACK_STAND => nextStateAfterStand(blackjackGameState, deck)
      case CommandService.BLACKJACK_DOUBLEDOWN => nextStateAfterDoubleDown(blackjackGameState, deck, nextCard)
      case CommandService.BLACKJACK_SPLIT => nextStateAfterSplit(blackjackGameState, deck)
      case unknownCommand => throw new NotImplementedException(s"Command $unknownCommand hasn't been implemented yet!")
    }
  }

  def isOpen(cards: List[BlackjackCard]): Boolean = {
    val openValues = calculateHandValues(cards) collect {
      case v: Int if isOpen(v) => v
    }
    openValues.nonEmpty
  }

  def isOpen(v: Int): Boolean = v >= 0 && v < 21

  def isBusted(cards: List[BlackjackCard]): Boolean = {
    val nonBustedValues = calculateHandValues(cards) collect {
      case v: Int if !isBusted(v) => v
    }
    nonBustedValues.isEmpty
  }

  def isBusted(v: Int) = v < 0 || v > 21

  def isBlackjack(cards: List[BlackjackCard]): Boolean = {
    val value21 = calculateHandValues(cards) collect {
      case v: Int if v == 21 => v
    }
    cards.length == 2 && value21.nonEmpty
  }

  def calculateHandValues(cards: List[BlackjackCard]): List[Int] = {
    val softHand = (cards map { c => getSoftValue(c) }).sum
    // Ace has valid both primary and secondary values
    val hasAce = (cards collect { case c if isValidValue(c.primaryValue) && isValidValue(c.secondaryValue) => c}).nonEmpty
    val hardHand = softHand + { if (hasAce) 10 else 0 }
    List(softHand, hardHand)
  }

  /**
    * Calcultes best hand value from the soft and hard values.
    *
    * @param handValues
    */
  def bestHandValue(handValues: List[Int]): Int = {
    val (soft, hard) = (handValues.head, handValues.last)
    if (hard <= 21) hard
    else soft
  }

  def getSoftValue(card: BlackjackCard): Int = {
    // If both values are valid return minimum
    // else return primary value (it is always valid)

    if (isValidValue(card.primaryValue) && isValidValue(card.secondaryValue)) Math.min(card.primaryValue, card.secondaryValue)
    else card.primaryValue
  }

  def isValidValue(value: Int): Boolean = value >= 1 && value <= 11


  // TODO: Include more info about the game, such as are there enough cards left or the deck has to be reshuffled, in that case
  // TODO: game status is BLACKJACK_GAME_FINISHED
  def getGameStatus(userHands: BlackjackHands)(implicit statusCodeService: StatusCodeService): StatusCode = {
    if (userHands.hands exists { h => h.status == BlackjackHandStatus.OPEN }) statusCodeService.blackjackRoundRunning
    else statusCodeService.blackjackRoundFinished
  }

  def userHandsWithOutcome(userHands: BlackjackHands, dealerHand: BlackjackHand, statusCode: Int)(implicit statusCodeService: StatusCodeService): BlackjackHands = {
    if (statusCodeService.blackjackRoundFinished.code == statusCode || statusCodeService.blackjackGameFinished.code == statusCode) {
      BlackjackHands(userHands.hands map { userHandWithOutcome(_, dealerHand) })
    } else {
      userHands
    }
  }

  def userHandWithOutcome(userHand: BlackjackHand, dealerHand: BlackjackHand): BlackjackHand = {
    // If user hand is empty then just return it
    if (userHand.status == BlackjackHandStatus.EMPTY || userHand.status == BlackjackHandStatus.OPEN) userHand
    // If player is BUSTED then outcome is LOST
    else if (userHand.status == BlackjackHandStatus.BUSTED) userHand.copy(outcome = BlackjackHandOutcome.LOST)
    else if (dealerHand.status == BlackjackHandStatus.BUSTED) userHand.copy(outcome = BlackjackHandOutcome.WON)
    else if (dealerHand.status == BlackjackHandStatus.STANDING &&
      (userHand.status == BlackjackHandStatus.STANDING || userHand.status == BlackjackHandStatus.DOUBLE_DOWN)) userHandWithOutcomeBasedOnScores(userHand, dealerHand)
    else throw new IllegalStateException(s"Unhandled state:\nUser hand: $userHand\nDealer hand: $dealerHand")
  }

  def userHandWithOutcomeBasedOnScores(userHand: BlackjackHand, dealerHand: BlackjackHand): BlackjackHand = {
    val userScore = bestHandValue(calculateHandValues(userHand.cards))
    val dealerScore = bestHandValue(calculateHandValues(dealerHand.cards))

    if (userScore > dealerScore) userHand.copy(outcome = BlackjackHandOutcome.WON)
    else if (userScore < dealerScore) userHand.copy(outcome = BlackjackHandOutcome.LOST)
    else userHand.copy(outcome = BlackjackHandOutcome.TIE)
  }

  def findAndUpdateFirstOpenHand(userHands: BlackjackHands, updateFunction: (BlackjackHand) => BlackjackHand): BlackjackHands = {
    var foundFirst = false
    BlackjackHands(userHands.hands map { h => if (!foundFirst && h.status == BlackjackHandStatus.OPEN) {
      foundFirst = true
      updateFunction(h)
    } else {
      h
    }})
  }
}
