package com.dashcasino.service

import com.dashcasino.model.{BlackjackGame, User}

/**
  * Created by freezing on 1/30/16.
  */
class BlackjackService {
  def bet(gameId: Int, amount: BigDecimal) = ???

  def `my cards`(gameId: Int) = ???

  def `dealer cards`(gameId: Int) = ???

  def hit(gameId: Int) = ???

  def `double-down`(gameId: Int) = ???

  def stand(gameId: Int) = ???

  def split(gameId: Int) = ???

  def insurance(gameId: Int) = ???
}
