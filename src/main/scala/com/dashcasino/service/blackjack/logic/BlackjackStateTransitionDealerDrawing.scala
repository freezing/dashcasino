package com.dashcasino.service.blackjack.logic

import com.dashcasino.dao.sql.BlackjackCardSqlDao
import com.dashcasino.model.{BlackjackHandStatus, BlackjackDeck, BlackjackHand, BlackjackHands}
import com.dashcasino.service.BlackjackService

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionDealerDrawing { self: BlackjackService =>
  def isGameFinished(userHands: BlackjackHands): Boolean = {
    val nonFinishedHands = userHands.hands collect { case hand if !isHandFinished(hand) => hand}
    nonFinishedHands.isEmpty
  }

  def isHandFinished(hand: BlackjackHand): Boolean = !isOpen(hand.cards)

  def getFinalDealerHand(userHands: BlackjackHands, dealerHand: BlackjackHand, deck: BlackjackDeck)
                        (implicit blackjackCardSqlDao: BlackjackCardSqlDao): BlackjackHand = {
    // Draw until not busted or soft-hand >= 17
    var newDealerHand = dealerHand

    var nextCardIdx = dealerHand.cards.length + userHands.hands.head.cards.length + userHands.hands(1).cards.length

    while (dealerShouldDraw(newDealerHand)) {
      val nextCard =  getCard(deck.order.cards(nextCardIdx))
      newDealerHand = updateDealerStatus(drawCard(newDealerHand, nextCard))
      nextCardIdx += 1
    }
    newDealerHand
  }

  def updateDealerStatus(hand: BlackjackHand): BlackjackHand = {
    val status = {
      val values = calculateHandValues(hand.cards)
      val nonBustedValues = values collect {
        case v: Int if v >= 1 && v <= 21 => v
      }

      val dealerOpenValues = nonBustedValues collect {
        case v: Int if v < 17 => v
      }

      if (nonBustedValues.isEmpty) BlackjackHandStatus.BUSTED
      else if (dealerOpenValues.isEmpty) BlackjackHandStatus.STANDING
      else BlackjackHandStatus.OPEN
    }
    hand.copy(status = status)
  }

  def dealerShouldDraw(hand: BlackjackHand): Boolean = hand.status == BlackjackHandStatus.OPEN
}
