package com.dashcasino.service.blackjack.commands

import com.dashcasino.dao.sql.BlackjackGameStateSqlDao
import com.dashcasino.model.{BlackjackHand, BlackjackGameState, BlackjackHands}
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor
import com.dashcasino.service.blackjack.{BlackjackService, BlackjackDeckService}

/**
  * Created by freezing on 1/31/16.
  */
trait BlackjackGetCardsCommands { self: BlackjackServiceActor =>
  // TODO: Check if it is user's game
  def `user cards`(userId: Int, gameId: Int)
      (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao): BlackjackHands = {
    val blackjackGameState = blackjackGameStateDao.findLastBlackjackGameState(gameId).get
    userHands(blackjackGameState)
  }

  // TODO: Check if it is user's game
  def `dealer cards`(userId: Int, gameId: Int)
                    (implicit blackjackDeckService: BlackjackDeckService, blackjackGameStateDao: BlackjackGameStateSqlDao) = {
    val blackjackGameState = blackjackGameStateDao.findLastBlackjackGameState(gameId).get
    dealerHand(blackjackGameState)
  }

  def userHands(state: BlackjackGameState): BlackjackHands = {
    decodeUserHands(state.userHand)
  }

  def dealerHand(state: BlackjackGameState): BlackjackHand = {
    decodeDealerHand(state.dealerHand)
  }
}
