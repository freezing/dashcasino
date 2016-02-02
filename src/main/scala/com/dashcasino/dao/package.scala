package com.dashcasino

import com.dashcasino.dao.sql._
import org.h2.mvstore.db.TransactionStore.Transaction
import scalikejdbc.{LoggingSQLAndTimeSettings, GlobalSettings, AutoSession, ConnectionPool}

/**
  * Created by freezing on 1/28/16.
  */
package object dao {
  // TODO: Read credentials from config file and enable LIVE and TEST modes
  // TODO: Instantiate all com daos as implicit

  // initialize JDBC driver & connection pool
  Class.forName("com.mysql.jdbc.Driver")
  // TODO: Move this to app
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

  class DaoStatusCode extends Enumeration {
    val OK = Value(0)
    val ERROR = Value(1)
    val SERVER_ERROR = Value(2)
  }
  object DaoStatusCode extends DaoStatusCode
  case class ResultCode(status: DaoStatusCode.Value, description: String)

  // Implicit instances for SqlDao
  implicit lazy val commandDao = new CommandSqlDao
  implicit lazy val accountDao = new AccountSqlDao
  implicit lazy val transactionDao = new TransactionSqlDao
  implicit lazy val userDao = new UserSqlDao
  implicit lazy val blackjackCardDao = new BlackjackCardSqlDao
  implicit lazy val blackjackDeckDao = new BlackjackDeckSqlDao
  implicit lazy val blackjackGameStateDao = new BlackjackGameStateSqlDao
  implicit lazy val blackjackGameDao = new BlackjackGameSqlDao
}
