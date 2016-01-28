package com.dashcasino.services.dao.sql

import scalikejdbc.{AutoSession, ConnectionPool, GlobalSettings, LoggingSQLAndTimeSettings}

/**
  * Created by freezing on 1/28/16.
  */
// TODO: Read credentials from config file and enable LIVE and TEST modes
trait SqlDao extends UserSqlDao with AccountSqlDao with BlackjackGameDao {
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
}

object Test extends App with SqlDao {

}