package com.dashcasino.service

import com.dashcasino.dao.sql.BlackjackCardSqlDao
import com.dashcasino.model._

import argonaut._, Argonaut._

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackDeckService(implicit blackjackCardSqlDao: BlackjackCardSqlDao) {
  type JsonString = String

  def getCard(code: Int) = blackjackCardSqlDao.findBlackjackCard(code)


  // TODO: Implement logic that returns hands
  def getUserHands(blackjackDeck: BlackjackDeck, gameStateOption: Option[BlackjackGameState]): BlackjackHands = {
    ???
  }

  def getDealerHand(blackjackDeck: BlackjackDeck, gameStateOption: Option[BlackjackGameState]): BlackjackHand = {
    ???
  }

  def getUserHandsAsJson(blackjackDeck: BlackjackDeck, gameStateOption: Option[BlackjackGameState]): JsonString =
    getUserHands(blackjackDeck, gameStateOption).toJson

  def getDealerHandAsJson(blackjackDeck: BlackjackDeck, gameStateOption: Option[BlackjackGameState]): JsonString =
    getDealerHand(blackjackDeck, gameStateOption).toJson
}
