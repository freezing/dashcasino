package com.dashcasino.service.blackjack.logic.statetransition

import com.dashcasino.dao.sql.BlackjackCardSqlDao
import com.dashcasino.model._
import com.dashcasino.service.{StatusCodeService, CommandService}

trait BlackjackStateTransitionSplit { self: BlackjackStateTransition =>
  def nextStateAfterSplit(oldState: BlackjackGameState, deck: BlackjackDeck)
                       (implicit blackjackCardDao: BlackjackCardSqlDao, commandService: CommandService, statusCodeService: StatusCodeService): BlackjackGameState = {
    // TODO: For now user can split only once, not sure if it is by the rules to split more than once
    val newUserBlackjackHands = BlackjackHands(splitHands(oldState.userHand.hands))
    oldState.copy(userHand = newUserBlackjackHands, commandCode = commandService.blackjackSplit.code)
  }

  private def splitHands(hands: List[BlackjackHand]): List[BlackjackHand] = {
    val firstHand = hands.head.copy(cards = List(hands.head.cards.head))
    val secondHand = hands.head.copy(cards = List(hands.head.cards.last))
    List(firstHand, secondHand)
  }
}
