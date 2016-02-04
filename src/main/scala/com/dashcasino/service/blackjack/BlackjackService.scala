package com.dashcasino.service.blackjack

import javax.rmi.CORBA.Tie

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.util.Timeout
import akka.pattern.ask
import com.dashcasino.model.{BlackjackGame, BlackjackGameState}
import com.dashcasino.service.CommandService
import com.dashcasino.service.account.AccountService
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Try, Failure, Success}

import scala.concurrent.duration._

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService(implicit actorSystem: ActorSystem, timeout: Timeout, blackjackDeckService: BlackjackDeckService, accountService: AccountService, commandService: CommandService) {
  private val NUMBER_OF_ACTORS = 16

  var gameActors = {
    // TODO: Later we can start with empty map: Map.empty[Int, ActorRef]
    // TODO: And create actors per need
    // For now just create 8 actors and each of them will cover games that have the same mod as it's index
    0 until NUMBER_OF_ACTORS map { moduo => moduo -> actorSystem.actorOf(Props(new BlackjackServiceActor)) } toMap
  }

  // TODO: Game creator should be separate service from BlackjackService
  val gameCreator = actorSystem.actorOf(Props(new BlackjackServiceActor))

  def defaultHandle(a: Any) = a match {
    case Success(s) => s.asInstanceOf[BlackjackGameState]
    case Failure(t) => throw t
  }

  def bet(msg: BlackjackBet): BlackjackGameState = {
    defaultHandle(Await.result(gameCreator ? msg, 10.seconds))
  }

  def hit(msg: BlackjackHit): BlackjackGameState = {
    defaultHandle(Await.result(actor(msg.gameId) ? msg, 10.seconds))
  }

  def stand(msg: BlackjackStand): BlackjackGameState = {
    defaultHandle(Await.result(actor(msg.gameId) ? msg, 10.seconds))
  }

  def doubleDown(msg: BlackjackDoubleDown): BlackjackGameState = {
    defaultHandle(Await.result(actor(msg.gameId) ? msg, 10.seconds))
  }

  def split(msg: BlackjackSplit): BlackjackGameState = {
    defaultHandle(Await.result(actor(msg.gameId) ? msg, 10.seconds))
  }

  def insurance(msg: BlackjackInsurance): BlackjackGameState = {
    defaultHandle(Await.result(actor(msg.gameId) ? msg, 10.seconds))
  }

  private def actor(gameId: Int): ActorRef = gameActors(gameId % NUMBER_OF_ACTORS)

  private def createActor(gameId: Int) = {}
}
