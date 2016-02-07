package com.dashcasino.service.blackjack.logic.actor

import akka.actor.Actor
import com.dashcasino.dao.sql.{BlackjackDeckSqlDao, BlackjackCardSqlDao, BlackjackGameStateSqlDao, BlackjackGameSqlDao}
import com.dashcasino.exception.{IllegalRequestException, AuthorizationException}
import com.dashcasino.model._
import com.dashcasino.service.{StatusCodeService, CommandService}
import com.dashcasino.service.account.{InternalDeposit, AccountService}
import com.dashcasino.service.blackjack._
import com.dashcasino.service.blackjack.commands._
import com.dashcasino.service.blackjack.logic.statetransition.BlackjackStateTransition

import argonaut._
import argonaut.Argonaut._

import scala.util.Try

/**
  * Created by freezing on 2/1/16.
  */
class BlackjackServiceActor(implicit val blackjackDeckService: BlackjackDeckService, blackjackGameDao: BlackjackGameSqlDao, blackjackGameStateDao: BlackjackGameStateSqlDao, blackjackCardDao: BlackjackCardSqlDao, blackjackDeckSqlDao: BlackjackDeckSqlDao, accountService: AccountService, commandService: CommandService, statusCodeService: StatusCodeService)
  extends Actor with BlackjackStateTransition with BlackjackBetCommand with BlackjackHitCommand with BlackjackStandCommand with BlackjackDoubleDownCommand {
  def receive = {
    // TODO: SPLIT, INSURANCE
    case msg: BlackjackBet => sender ! Try(bet(msg))
    case msg: BlackjackStand => sender ! Try(stand(msg))
    case msg: BlackjackHit => sender ! Try(hit(msg))
    case msg: BlackjackDoubleDown => sender ! Try(`double-down`(msg))
    case msg: BlackjackSplit => throw new NotImplementedError(msg.toString)
    case msg: BlackjackInsurance => throw new NotImplementedError(msg.toString)
    case unknown => throw new IllegalArgumentException(s"Unknown message: $unknown")
  }

  def checkAuthorization(userId: Int, game: BlackjackGame): Unit = {
    if (userId != game.userId) throw new AuthorizationException(s"User $userId has no authorization over game: $game")
  }

  def getDeck(deckId: Int): BlackjackDeck =
    blackjackDeckSqlDao.findBlackjackDeck(deckId) match {
      case Some(x) => x
      // TODO: Throw invalid blackjack deck exception (it doesn't have to be handled here, it can throw exception on a lower level - i.e. DAO
      case None => throw new IllegalArgumentException(s"Invalid blackjack deck id: $deckId")
    }

  def decodeDealerHand(json: String): BlackjackHand = json.decodeOption[BlackjackHand] match {
    case Some(x) => x
    // TODO: Send email report
    case None => throw new IllegalStateException(s"Invalid blackjack dealer hand json: $json")
  }

  def decodeUserHands(json: String): BlackjackHands = json.decodeOption[BlackjackHands] match {
    case Some(x) => x
    // TODO: Send email report
    case None => throw new IllegalStateException(s"Invalid blackjack user hands json: $json")
  }

  def getGame(gameId: Int): BlackjackGame = blackjackGameDao.findBlackjackGame(gameId) match {
    case Some(game) => game
    // TODO: Send email report
    case None => throw new IllegalRequestException(s"Invalid game id: $gameId")
  }

  def currentUserHand(state: BlackjackGameState): BlackjackHand = {
    (state.userHand.hands collectFirst { case hand if hand.status == BlackjackHandStatus.OPEN => hand }).head
  }

  def processPayment(userId: Int, gameState: BlackjackGameState): BlackjackGameState = {
    val userHand = BlackjackHands(gameState.userHand.hands map { h => processHandPayment(userId, h) })
    val newGameState = gameState.copy(userHand = userHand)
    if (newGameState != gameState) {
      blackjackGameStateDao.insertBlackjackGameState(newGameState)
    }
    newGameState
  }

  def processHandPayment(userId: Int, hand: BlackjackHand): BlackjackHand = {
    // Check if hand should get any money
    if (!hand.paymentFinished && hand.outcome != BlackjackHandOutcome.PENDING) {
      val returnMoney = hand.outcome match {
        case BlackjackHandOutcome.LOST => BigDecimal(0.0)
        case BlackjackHandOutcome.TIE => hand.money
        case BlackjackHandOutcome.WON => hand.money * 2
          // TODO: Make Blackjack payment ratio dynamic
        case BlackjackHandOutcome.WON_BLACKJACK => hand.money + hand.money * 3.0 / 2.0
        case unknown => throw new IllegalStateException(s"Uknown outcome: $unknown")
      }
      accountService.internalDeposit(InternalDeposit(userId, returnMoney, "{description: User has won}"))
      hand.copy(paymentFinished = true)
    } else {
      hand
    }
  }
}
