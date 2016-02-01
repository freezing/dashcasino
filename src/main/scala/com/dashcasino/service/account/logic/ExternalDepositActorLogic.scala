package com.dashcasino.service.account.logic

import com.dashcasino.dao.sql.TransactionSqlDao
import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.model.Transaction
import com.dashcasino.service.CommandService
import com.dashcasino.service.account.{ExternalDeposit, AccountServiceActor}

/**
  * Created by freezing on 2/1/16.
  */
trait ExternalDepositActorLogic { self: AccountServiceActor =>
  def externalDeposit(msg: ExternalDeposit)
                     (implicit transactionDao: TransactionSqlDao, commandService: CommandService): Boolean = {
    val (userId, amount, reason) = ExternalDeposit.unapply(msg).get

    val account = accountDao.findAccount(userId).get
    checkMoney(account, amount)

    val newTransaction = Transaction(-1, account.id, amount, commandService.EXTERNAL_DEPOSIT, reason, NOT_CONFIRMED, -1)
    transactionDao.insertTransaction(newTransaction)

    accountDao.updateMoney(account.id, amount)

    val confirmedTransaction = Transaction(-1, account.id, amount, commandService.EXTERNAL_DEPOSIT, reason, CONFIRMED, -1)
    transactionDao.insertTransaction(confirmedTransaction)
    true
  }
}
