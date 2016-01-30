package com.dashcasino.service

import com.dashcasino.dao.sql.{BlackjackDeckSqlDao, BlackjackCardSqlDao}
import com.dashcasino.model._

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackDeckService(implicit val blackjackDeckDao: BlackjackDeckSqlDao, blackjackCardSqlDao: BlackjackCardSqlDao) {
  type JsonString = String


  def getCard(code: Int) = blackjackCardSqlDao.findBlackjackCard(code)


  // TODO: Implement logic that returns hands
  def getUserHands(blackjackDeck: BlackjackDeck, gameStateOption: Option[BlackjackGameState]): BlackjackHands = {
    ???
  }

  def getDealerHand(blackjackDeck: BlackjackDeck, gameStateOption: Option[BlackjackGameState]): BlackjackHand = {
    ???
  }

  def getUserHands(blackjackDeckId: Int, gameStateOption: Option[BlackjackGameState]): BlackjackHands = {
    blackjackDeckDao.findBlackjackDeck(blackjackDeckId) match {
      case Some(deck) => getUserHands(deck, gameStateOption)
      // TODO: SEND EMAIL REPORT AS THIS SHOULD NEVER HAPPEN AND THROW DeckNotFoundException
      case None => throw new Exception("Deck not found")
    }
  }

  def getDealerHand(blackjackDeckId: Int, gameStateOption: Option[BlackjackGameState]): BlackjackHand = {
    blackjackDeckDao.findBlackjackDeck(blackjackDeckId) match {
      case Some(deck) => getDealerHand(deck, gameStateOption)
      // TODO: SEND EMAIL REPORT AS THIS SHOULD NEVER HAPPEN AND THROW DeckNotFoundException
      case None => throw new Exception("Deck not found")
    }
  }
}
