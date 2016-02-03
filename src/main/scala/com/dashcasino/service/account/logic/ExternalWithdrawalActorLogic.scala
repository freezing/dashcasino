package com.dashcasino.service.account.logic

import com.dashcasino.dao.sql.TransactionSqlDao
import com.dashcasino.dao.{DaoStatusCode, ResultCode}
import com.dashcasino.model.{Transaction, User}
import com.dashcasino.service.CommandService
import com.dashcasino.service.account.{ExternalWithdrawal, AccountServiceActor}
import com.dashcasino.service.wallet.WalletService

/**
  * Created by freezing on 2/1/16.
  */
trait ExternalWithdrawalActorLogic { self: AccountServiceActor =>
  // TODO: Check if it is better to have user as parameter, or account.
  def externalWithdrawal(msg: ExternalWithdrawal)
                      (implicit transactionDao: TransactionSqlDao, commandService: CommandService, walletService: WalletService): Boolean = {
    val (userId, payoutAddress, amount) = ExternalWithdrawal.unapply(msg).get
    // TODO: Make sure user exists, it has valid id, some checks since this is very important
    // TODO: If user is sent as parameter we have to get account from the database
    val account = accountDao.findAccount(userId).get
    // Check if user has enough money in the account
    checkMoney(account, amount)

    // Create transaction and insert it in the database
    val reason = s"{payoutAddress: $payoutAddress, amount: $amount, userId: $userId}"
    // NOTE: Amount is -amount since this transaction withdraws the money from the account
    val newTransaction = Transaction(-1, account.id, -amount, reason, NOT_CONFIRMED, -1)
    transactionDao.insertTransaction(newTransaction)

    // Update account
    accountDao.updateMoney(account.id, -amount)

    // TODO: What happens if this fails since wallet might fail to send money and transaction is inserted in the database
    walletService.sendMoney(payoutAddress, amount)

    // Confirm transaction
    val confirmedTransaction = Transaction(-1, account.id, -amount, reason, CONFIRMED, -1)
    transactionDao.insertTransaction(confirmedTransaction)
    true
  }
}
