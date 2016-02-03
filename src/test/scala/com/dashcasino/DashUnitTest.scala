package com.dashcasino

import com.dashcasino.dao.sql._
import com.dashcasino.service.blackjack.{BlackjackService, BlackjackDeckService}
import com.dashcasino.service.{UserService, CommandService}
import com.dashcasino.service.account.AccountService
import com.dashcasino.service.wallet.WalletService
import com.mysql.jdbc.TimeUtil
import org.scalatest.{Matchers, FlatSpec}
import scalikejdbc.{LoggingSQLAndTimeSettings, GlobalSettings, AutoSession, ConnectionPool}

/**
  * Created by freezing on 2/2/16.
  */
class DashUnitTest extends FlatSpec with Matchers {
  // TODO: Read credentials from config file and enable LIVE and TEST modes
  // TODO: Instantiate all com daos as implicit

  class WalletTestService extends WalletService {
    override def sendMoney(payoutAddress: String, amount: BigDecimal): Unit = {
      // We don't want to send any money while in unit tests.
      // Maybe in future if we setup test wallet service
    }
  }

  // initialize JDBC driver & connection pool
  Class.forName("com.mysql.jdbc.Driver")
  // TODO: USE TEST DATABASE WHEN FIGURED OUT HOW TO SETUP
  ConnectionPool.singleton("jdbc:mysql://33.33.33.10:3306/dashcasino", "root", "root")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession

  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
    enabled = false,
    singleLineMode = false,
    printUnprocessedStackTrace = false,
    stackTraceDepth= 15,
    logLevel = 'debug,
    warningEnabled = false,
    warningThresholdMillis = 3000L,
    warningLogLevel = 'info
  )

  // Implicit instances for SqlDao
  implicit lazy val commandDao = new CommandSqlDao
  implicit lazy val accountDao = new AccountSqlDao
  implicit lazy val transactionDao = new TransactionSqlDao
  implicit lazy val userDao = new UserSqlDao
  implicit lazy val blackjackCardDao = new BlackjackCardSqlDao
  implicit lazy val blackjackDeckDao = new BlackjackDeckSqlDao
  implicit lazy val blackjackGameStateDao = new BlackjackGameStateSqlDao
  implicit lazy val blackjackGameDao = new BlackjackGameSqlDao

  // Implicit services
  implicit lazy val commandService = new CommandService
  implicit lazy val walletService = new WalletTestService
  implicit lazy val accountService = new AccountService
  implicit lazy val userService = new UserService
  implicit lazy val blackjackDeckService = new BlackjackDeckService
  implicit lazy val blackjackService = new BlackjackService

  init

  def init = {
    // Clear all tables
    import scalikejdbc._
    sql"DELETE FROM Transaction".update.apply
    sql"DELETE FROM Account".update.apply
    sql"DELETE FROM User".update.apply
    sql"DELETE FROM BlackjackGameState".update.apply
    sql"DELETE FROM BlackjackGame".update.apply
    sql"DELETE FROM BlackjackDeck".update.apply
  }
}
