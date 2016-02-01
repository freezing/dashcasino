package com.dashcasino.service.account.logic

import com.dashcasino.service.account.{InternalWithdrawal, AccountServiceActor}

/**
  * Created by freezing on 2/1/16.
  */
trait InternalWithdrawalActorLogic { self: AccountServiceActor =>
  /**
    * This is not thread safe on account level (can be done by using SQL transactions but if changed to NoSQL later it would make no sense.
    * Thread-safe should be handled in the top level Actor that will process all messages per Account (which is good solution).
    * @return
    */
  // TODO: Check if Actor can send Unit value to another Actor which means it is a success with no value
  def internalWithdrawal(msg: InternalWithdrawal): Boolean = {
    // TODO: Figure out how to extract case class, something like: a @ (userId, gameId, commandId, amount, reason)
    val (userId, gameId, commandId, amount, reason) = InternalWithdrawal.unapply(msg).get
    val account = accountDao.findAccount(userId).get
    checkMoney(account, amount)
    internalUpdate(account, gameId, commandId, -amount, reason)
    true
  }
}
