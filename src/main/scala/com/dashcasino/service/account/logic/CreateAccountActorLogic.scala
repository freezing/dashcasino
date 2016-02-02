package com.dashcasino.service.account.logic

import com.dashcasino.dao.sql.AccountSqlDao
import com.dashcasino.model.{Account, User}
import com.dashcasino.service.account.AccountServiceActor
import com.dashcasino.service.wallet.WalletService

/**
  * Created by freezing on 2/2/16.
  */
trait CreateAccountActorLogic { self: AccountServiceActor =>
  def createAccount(user: User)(implicit accountSqlDao: AccountSqlDao, walletService: WalletService): Boolean = {
    val depositAddress = walletService.generateDepositAddress
    val account = Account(-1, user.id, depositAddress, 0.0)
    accountSqlDao.insertAccount(account)
    true
  }
}