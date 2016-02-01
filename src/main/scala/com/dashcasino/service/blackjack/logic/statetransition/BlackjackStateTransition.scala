package com.dashcasino.service.blackjack.logic.statetransition

import argonaut.Argonaut._
import argonaut._
import com.dashcasino.dao.sql.{BlackjackCardSqlDao, BlackjackDeckSqlDao}
import com.dashcasino.model._
import com.dashcasino.service.CommandService
import com.dashcasino.service.blackjack.BlackjackService
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import spray.util.NotImplementedException

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransition extends BlackjackStateTransitionHit with BlackjackStateTransitionDealerDrawing with BlackjackStateTransitionStand with BlackjackStateTransitionDoubleDown { self: BlackjackServiceActor =>
  def hideIfNotBlackjack(hand: BlackjackHand): BlackjackHand = {
    isBlackjack(hand.cards) match {
      case true => hand.copy(status = BlackjackHandStatus.BLACKJACK)
      case false => hand.copy(cards = List(getCard(BlackjackCardCodes.FACE_DOWN), hand.cards(1)))
    }
  }

  def drawCard(hand: BlackjackHand, card: BlackjackCard): BlackjackHand = {
    val newCards = hand.cards ++ List(card)
    val status = getStatus(newCards)
    hand.copy(cards = newCards, status = status)
  }

  def getStatus(cards: List[BlackjackCard]) = {
    if (isBlackjack(cards)) BlackjackHandStatus.BLACKJACK
    else if (isOpen(cards)) BlackjackHandStatus.OPEN
    else if (isBusted(cards)) BlackjackHandStatus.BUSTED
    else BlackjackHandStatus.STANDING
  }

  def createHand(firstCardCode: Int, secondCardCode: Int, money: BigDecimal): BlackjackHand = {
    val card1 = getCard(firstCardCode)
    val card2 = getCard(secondCardCode)
    val cards = List(card1, card2)
    BlackjackHand(cards, getStatus(cards), money)
  }

  def createInitialUserHand(deck: BlackjackDeck, money: BigDecimal): BlackjackHand =
    createHand(deck.order.cards(0), deck.order.cards(2), money)

  def createInitialDealerHand(deck: BlackjackDeck, money: BigDecimal): BlackjackHand =
    hideIfNotBlackjack(createHand(deck.order.cards(1), deck.order.cards(3), money))

  /**
    * Initial state is created by the following rules:
    * 1. Draw card and deal it to player face-up
    * 2. Draw card and deal it to dealer face-down
    * 3. Draw card and deal it to player face-up
    * 4. Draw card and deal it to dealer face-up
 *
    * @param blackjackDeckId Deck id used to retrieve deck configuration (i.e. order)
    * @param blackjackDeckDao
    * @param blackjackCardDao
    * @return Initial BlackjackGameState after the BET is placed.
    */
  def getInitialState(blackjackDeckId: Int, gameId: Int, money: BigDecimal)(implicit blackjackDeckDao: BlackjackDeckSqlDao, blackjackCardDao: BlackjackCardSqlDao, commandService: CommandService): BlackjackGameState = {
    val deck = getDeck(blackjackDeckId)

    // User hand is created from cards at indices 0 and 2
    val firstUserHand = createInitialUserHand(deck, money)
    val userBlackjackHands = List(firstUserHand, BlackjackHand(List.empty[BlackjackCard], BlackjackHandStatus.EMPTY, 0.0))
    val userHand = BlackjackHands(userBlackjackHands)

    val description = "Nothing for now"
    val dealerHand = createInitialDealerHand(deck, money)
    val statusCode = 0 // TODO: Figure out what is the status code and if we need it at all

    BlackjackGameState(-1, gameId, userHand.asJson.spaces2, dealerHand.asJson.spaces2, description, commandService.blackjackBet.code, statusCode, 0 /* no insurance at start */, -1)
  }

  def getNextState(blackjackDeckId: Int, blackjackGameState: BlackjackGameState, command: Command)
                  (implicit blackjackDeckDao: BlackjackDeckSqlDao, blackjackCardDao: BlackjackCardSqlDao): BlackjackGameState = {
    val deck = getDeck(blackjackDeckId)
    val dealerHand = decodeDealerHand(blackjackGameState.dealerHand)
    val userHands = decodeUserHands(blackjackGameState.userHand)

    // Draw next card for the state
    val nextCard = deck.order.cards(dealerHand.cards.length + userHands.hands.head.cards.length + userHands.hands(1).cards.length)

    // TODO: Think of a way to refactor. This is not very nice
    command.name match {
      case CommandService.BLACKJACK_HIT => nextStateAfterHit(blackjackGameState, deck, userHands, dealerHand, nextCard)
      case CommandService.BLACKJACK_STAND => nextStateAfterStand(blackjackGameState, userHands)
      //case CommandService.BLACKJACK_DOUBLEDOWN => nextStateAfterDoubleDown(blackjackGameState, userHands)
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

  def getSoftValue(card: BlackjackCard): Int = {
    // If both values are valid return minimum
    // else return primary value (it is always valid)

    if (isValidValue(card.primaryValue) && isValidValue(card.secondaryValue)) Math.min(card.primaryValue, card.secondaryValue)
    else card.primaryValue
  }

  def isValidValue(value: Int): Boolean = value >= 1 && value <= 11
}
