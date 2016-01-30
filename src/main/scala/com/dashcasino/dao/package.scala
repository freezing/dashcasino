package com.dashcasino

import com.dashcasino.dao.sql._
import org.h2.mvstore.db.TransactionStore.Transaction
import scalikejdbc.{LoggingSQLAndTimeSettings, GlobalSettings, AutoSession, ConnectionPool}

/**
  * Created by freezing on 1/28/16.
  */
package object dao {
  // TODO: Read credentials from config file and enable LIVE and TEST modes
  // TODO: Instantiate all sql daos as implicit

  // initialize JDBC driver & connection pool
  Class.forName("com.mysql.jdbc.Driver")
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
  implicit val commandDao = new CommandSqlDao
  implicit val accountDao = new AccountSqlDao
  implicit val transactionDao = new TransactionSqlDao
  implicit val userDao = new UserSqlDao
  implicit val blackjackCardDao = new BlackjackCardSqlDao
  implicit val blackjackDeckDao = new BlackjackDeckSqlDao
  implicit val blackjackGameStateDao = new BlackjackGameStateSqlDao
  implicit val blackjackGameDao = new BlackjackGameSqlDao
}
