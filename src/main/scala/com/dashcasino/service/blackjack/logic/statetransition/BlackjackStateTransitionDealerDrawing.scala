package com.dashcasino.service.blackjack.logic.statetransition

import com.dashcasino.dao.sql.BlackjackCardSqlDao
import com.dashcasino.model.{BlackjackDeck, BlackjackHand, BlackjackHandStatus, BlackjackHands}
import com.dashcasino.service.blackjack.BlackjackService
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackStateTransitionDealerDrawing { self: BlackjackServiceActor =>
  def getFinalDealerHand(userHands: BlackjackHands, dealerHand: BlackjackHand, deck: BlackjackDeck)
                        (implicit blackjackCardSqlDao: BlackjackCardSqlDao): BlackjackHand = {
    // Draw until not busted or soft-hand >= 17
    var newDealerHand = unhideDealerFirstCard(dealerHand, deck)

    var nextCardIdx = dealerHand.cards.length + userHands.hands.head.cards.length + userHands.hands(1).cards.length

    while (dealerShouldDraw(newDealerHand)) {
      val nextCard =  getCard(deck.order.cards(nextCardIdx))
      newDealerHand = updateDealerStatus(drawCard(newDealerHand, nextCard))
      nextCardIdx += 1
    }
    newDealerHand
  }

  // TODO: This is now HARDCODED for only ONE ROUND per deck shuffle, this should be changed to support more rounds per deck shuffle
  // TODO: In order to do that, we need some indicator that will say what was the starting position of the new round.
  // TODO: New colletion should be added such as BlackjackRound which will contain starting card position and game id.
  def unhideDealerFirstCard(dealerHand: BlackjackHand, blackjackDeck: BlackjackDeck): BlackjackHand = {
    createDealerHand(blackjackDeck.order.cards(1), blackjackDeck.order.cards(3), dealerHand.money)
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
      else BlackjackHandStatus.DEALER
    }
    hand.copy(status = status)
  }

  // Dealer hand status DEALER is similar to user's hand status DEALER
  def dealerShouldDraw(hand: BlackjackHand): Boolean = hand.status == BlackjackHandStatus.DEALER
}
