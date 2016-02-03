package com.dashcasino.service

import com.dashcasino.DashUnitTest
import com.dashcasino.model.{Transaction, User}
import com.dashcasino.service.account.{ExternalWithdrawal, InternalWithdrawal, ExternalDeposit, InternalDeposit}

/**
  * Created by freezing on 2/3/16.
  */
class AccountServiceTest extends DashUnitTest {
  lazy val user = userService.registerUser(User(-1, "accountservicetest@gmail.com", "testpass", -1))
  lazy val accountOption = accountDao.findAccount(user.id)

  val NOT_CONFIRMED = 0
  val CONFIRMED = 1

  def last2(transactions: List[Transaction]): List[Transaction] = {
    List(transactions(transactions.length - 2), transactions(transactions.length - 1))
  }

  def checkTransaction(transaction: Transaction, accountId: Int, amount: BigDecimal, reason: String, confirmationStatus: Int): Unit = {
    transaction.accountId should be (accountId)
    transaction.amount should be (amount)
    transaction.reason should be (reason)
    transaction.confirmed should be (confirmationStatus)
  }

  def checkTransactions(transactions: List[Transaction], accountId: Int, amount: BigDecimal, reason: String): Unit = {
    checkTransaction(transactions.head, accountId, amount, reason, NOT_CONFIRMED)
    checkTransaction(transactions.last, accountId, amount, reason, CONFIRMED)
  }

  "AccountService" should "insert transaction for all methods and update account" in {
    assume(accountOption.isDefined)

    val account = accountOption.get

    val externalDepositMoney = BigDecimal(10.0)
    val externalDepositReason = "{description: \"User wants to play some blackjack\"}"

    val internalWithdrawalMoney = BigDecimal(4.7)
    val internalWithdrawalReason = "{CommandName: \"BLACKJACK_BET\"}"

    val internalDepositMoney = BigDecimal(6.0)
    val internalDepositReason = "{CommandName: \"User WON with BLACKJACK\"}"

    val externalWithdrawalMoney = BigDecimal(3.5)
    val payoutAddress = walletService.generateDepositAddress
    val externalWithdrawalReason = s"{payoutAddress: $payoutAddress, amount: $externalWithdrawalMoney, userId: ${user.id}}"

    // Make sure that account has no money at the beginning
    account.amount should be (BigDecimal(0.0))

    // Deposit some money
    var accountCurrentMoney = BigDecimal(0)
    accountService.externalDeposit(ExternalDeposit(user.id, externalDepositMoney, externalDepositReason))
    // Check that account balance has updated
    accountCurrentMoney += externalDepositMoney
    accountDao.findAccount(user.id).get.amount should be (accountCurrentMoney)
    // Check that last two transactions are NEW and CONFIRMED and correspond to external deposit
    checkTransactions(last2(transactionDao.findTransactions(account.id)), account.id, externalDepositMoney, externalDepositReason)

    // Withdraw money internally
    accountService.internalWithdrawal(InternalWithdrawal(user.id, internalWithdrawalMoney, internalWithdrawalReason))
    // Check that account balance has updated
    accountCurrentMoney -= internalWithdrawalMoney
    accountDao.findAccount(user.id).get.amount should be (accountCurrentMoney)
    // Check that last two transactions are NEW and CONFIRMED and correspond to internal withdrawal
    checkTransactions(last2(transactionDao.findTransactions(account.id)), account.id, -internalWithdrawalMoney, internalWithdrawalReason)

    // Deposit money internally
    accountService.internalDeposit(InternalDeposit(user.id, internalDepositMoney, internalDepositReason))
    // Check that account balance has updated
    accountCurrentMoney += internalDepositMoney
    accountDao.findAccount(user.id).get.amount should be (accountCurrentMoney)
    // Check that last two transactions are NEW and CONFIRMED and correspond to internal deposit
    checkTransactions(last2(transactionDao.findTransactions(account.id)), account.id, internalDepositMoney, internalDepositReason)

    // Withdraw some money external
    accountService.externalWithdrawal(ExternalWithdrawal(user.id, payoutAddress, externalWithdrawalMoney))
    // Check that account balance has updated
    accountCurrentMoney -= externalWithdrawalMoney
    accountDao.findAccount(user.id).get.amount should be (accountCurrentMoney)
    // Check that last two transactions are NEW and CONFIRMED and correspond to external withdrawal
    checkTransactions(last2(transactionDao.findTransactions(account.id)), account.id, -externalWithdrawalMoney, externalWithdrawalReason)

    // Make sure that there are only 4 transactions
    transactionDao.findTransactions(account.id).length should be (8) // NEW and CONFIRMED for each
  }
}
