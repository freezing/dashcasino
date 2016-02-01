package com.dashcasino.service.account.logic

import com.dashcasino.service.account.{InternalDeposit, AccountServiceActor}

/**
  * Created by freezing on 2/1/16.
  */
trait InternalDepositActorLogic { self: AccountServiceActor =>
  def internalDeposit(msg: InternalDeposit): Boolean = {
    val (userId, gameId, commandId, amount, reason) = InternalDeposit.unapply(msg).get
    val account = accountDao.findAccount(userId).get
    internalUpdate(account, gameId, commandId, amount, reason)
    true
  }
}
