package com.dashcasino.service.blackjack

import java.security.MessageDigest
import java.util.UUID

import com.dashcasino.dao.sql.BlackjackDeckSqlDao
import com.dashcasino.exception.InvalidServerSecretException
import com.dashcasino.model.{BlackjackDeckOrder, BlackjackDeck}

import scala.util.Random

/**
  * Created by freezing on 1/30/16.
  */

class BlackjackDeckService(implicit val blackjackDeckSqlDao: BlackjackDeckSqlDao) {

  val NOT_SIGNED = false
  val SIGNED = true

  def generateServerSeed = UUID.randomUUID().getLeastSignificantBits

  def newDeck: BlackjackDeck = {
    val serverSeed = generateServerSeed
    // It's not really important since it will be shuffled again but it's nice to keep track of it
    val order = initialShuffle(serverSeed)
    // TODO: Figure out if long.toString is okay for this
    val serverSecret = makeSercret(serverSeed.toString, order)
    blackjackDeckSqlDao.insertBlackjackDeck(BlackjackDeck(-1, order, serverSeed, "", serverSecret, "", NOT_SIGNED, -1)).get
  }

  def digitalSign(deckId: Int, serverSecret: String, clientSeed: String): BlackjackDeck = {
    val unsignedDeck = blackjackDeckSqlDao.findBlackjackDeck(deckId).get
    if (unsignedDeck.serverSecret != serverSecret) throw new InvalidServerSecretException

    // Copy deck with SIGNED attribute
    val signedDeck = signDeck(unsignedDeck, clientSeed)
    blackjackDeckSqlDao.insertBlackjackDeck(signedDeck).get
  }

  def initialShuffle(serverSeed: Long): BlackjackDeckOrder = BlackjackDeckOrder(fisherYatesShuffle(1 to 52, serverSeed))

  def signDeck(unsignedDeck: BlackjackDeck, clientSeed: String): BlackjackDeck = {
    val signedDeck = createSignedDeck(unsignedDeck, clientSeed)
    blackjackDeckSqlDao.insertBlackjackDeck(signedDeck).get
  }

  // Note that we could use the order but this way we can keep it cleaner since here it is actually created as a whole
  private def createSignedDeck(deck: BlackjackDeck, clientSeed: String): BlackjackDeck = {
    val marsenneOrder = marsenneTwister(deck.order, clientSeed)
    val finalSecret = makeSercret(clientSeed, marsenneOrder)
    deck.copy(order = marsenneOrder, clientSeed = clientSeed, finalSecret = finalSecret, isSigned = SIGNED)
  }

  private def marsenneTwister(order: BlackjackDeckOrder, clientSeed: String): BlackjackDeckOrder = {
    // TODO: Implement Marsenne Twister algorithm
    order
  }

  private def makeSercret(seed: String, order: BlackjackDeckOrder): String = {
    // TODO: This has to be tested very well and figure out what does it actually do so that any user can create it himself
    // TODO: Include ORDER
    MessageDigest.getInstance("SHA-1").digest(seed.getBytes("utf-8")).toString
  }

  def fisherYatesShuffle(a: List[Int], seed: Long): List[Int] = {
    val rnd = new Random(seed)
    val array = a.toArray
    for (i <- array.indices.reverse diff Range(0, 1)) {
      // Swap them
      val j = rnd.nextInt(i)
      val tmp = array(i)
      array(i) = array(j)
      array(j) = tmp
    }
    array.toList
  }

  def fisherYatesShuffle(a: Seq[Int], seed: Long): List[Int] = fisherYatesShuffle(a.toList, seed)
}
