package com.dashcasino.service.blackjack

import javax.rmi.CORBA.Tie

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.util.Timeout
import akka.pattern.ask
import com.dashcasino.model.{BlackjackGame, BlackjackGameState}
import com.dashcasino.service.blackjack.logic.actor.BlackjackServiceActor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService(implicit actorSystem: ActorSystem, timeout: Timeout) {
  private val NUMBER_OF_ACTORS = 16

  var gameActors = {
    // TODO: Later we can start with empty map: Map.empty[Int, ActorRef]
    // TODO: And create actors per need
    // For now just create 8 actors and each of them will cover games that have the same mod as it's index
    0 until NUMBER_OF_ACTORS map { moduo => moduo -> actorSystem.actorOf(Props[BlackjackServiceActor], s"blackjackGameManager_$moduo") } toMap
  }

  // TODO: Game creator should be separate service from BlackjackService
  val gameCreator = actorSystem.actorOf(Props[BlackjackServiceActor], "blackjackGameCreator")

  def bet(msg: BlackjackBet) = {
    var gameState: BlackjackGameState = null
    gameCreator ? msg onComplete {
      case Success(res) => gameState = res.asInstanceOf[BlackjackGameState]
      case Failure(t) => throw t
    }
    // TODO: createActor(gameState.gameId) - use this later for each game when you figure out how to dispose them (maybe GC will do that somehow by MAGIC)
    gameState
  }

  def hit(msg: BlackjackHit) = {
    var gameState: BlackjackGameState = null
    actor(msg.gameId) ? msg onComplete {
      case Success(res) => gameState = res.asInstanceOf[BlackjackGameState]
      case Failure(t) => throw t
    }
    gameState
  }

  def stand(msg: BlackjackStand) = {
    var gameState: BlackjackGameState = null
    actor(msg.gameId) ? msg onComplete {
      case Success(res) => gameState = res.asInstanceOf[BlackjackGameState]
      case Failure(t) => throw t
    }
    gameState
  }

  def doubleDown(msg: BlackjackDoubleDown) = {
    var gameState: BlackjackGameState = null
    actor(msg.gameId) ? msg onComplete {
      case Success(res) => gameState = res.asInstanceOf[BlackjackGameState]
      case Failure(t) => throw t
    }
    gameState
  }

  def split(msg: BlackjackSplit) = {
    var gameState: BlackjackGameState = null
    actor(msg.gameId) ? msg onComplete {
      case Success(res) => gameState = res.asInstanceOf[BlackjackGameState]
      case Failure(t) => throw t
    }
    gameState
  }

  def insurance(msg: BlackjackInsurance) = {
    var gameState: BlackjackGameState = null
    actor(msg.gameId) ? msg onComplete {
      case Success(res) => gameState = res.asInstanceOf[BlackjackGameState]
      case Failure(t) => throw t
    }
    gameState
  }

  def actor(gameId: Int): ActorRef = gameActors(gameId % NUMBER_OF_ACTORS)

  def createActor(gameId: Int) = {}
}
