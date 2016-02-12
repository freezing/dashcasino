package com.dashcasino.server

import java.net.InetAddress

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import com.dashcasino.api.HttpApiServiceActor
import com.dashcasino.dao.sql._
import com.dashcasino.service.account.AccountService
import com.dashcasino.service.blackjack.{BlackjackService, BlackjackDeckService}
import com.dashcasino.service.wallet.WalletService
import com.dashcasino.service.{UserService, CommandService, StatusCodeService}
import scalikejdbc.{LoggingSQLAndTimeSettings, GlobalSettings, AutoSession, ConnectionPool}
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask

trait ImplicitDefinitions {
  // Need an actor system to host application in
  implicit val timeout = Timeout(5.seconds)
  implicit val actorSystem = ActorSystem("dash-casino-web-server")

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

  // Implicit instances for SqlDao
  implicit lazy val statusDao = new StatusCodeSqlDao
  implicit lazy val commandDao = new CommandSqlDao
  implicit lazy val accountDao = new AccountSqlDao
  implicit lazy val transactionDao = new TransactionSqlDao
  implicit lazy val userDao = new UserSqlDao
  implicit lazy val blackjackCardDao = new BlackjackCardSqlDao
  implicit lazy val blackjackDeckDao = new BlackjackDeckSqlDao
  implicit lazy val blackjackGameStateDao = new BlackjackGameStateSqlDao
  implicit lazy val blackjackGameDao = new BlackjackGameSqlDao

  // Implicit services
  implicit lazy val statusService = new StatusCodeService
  implicit lazy val commandService = new CommandService
  implicit lazy val walletService = new WalletService
  implicit lazy val accountService = new AccountService
  implicit lazy val userService = new UserService
  implicit lazy val blackjackDeckService = new BlackjackDeckService
  implicit lazy val blackjackService = new BlackjackService
}

/**
  * Created by freezing on 2/12/16.
  */
object DashCasinoWebServer extends App with ImplicitDefinitions {
  val p = (args.toSeq sliding 2 collectFirst { case Seq("--port", p) => p }).get.toInt

  // Create and start our service actor
  val service = actorSystem.actorOf(Props(new HttpApiServiceActor), "web-server-actor")

  // Start a new HTTP server
  IO(Http) ? Http.Bind(service, interface = InetAddress.getLocalHost.getHostName, port = p)
}
