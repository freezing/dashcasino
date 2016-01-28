# ************************************************************
# Sequel Pro SQL dump
# Version 4499
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 33.33.33.10 (MySQL 5.5.47-0ubuntu0.12.04.1)
# Database: dashcasino
# Generation Time: 2016-01-28 21:48:50 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Account`;

CREATE TABLE `Account` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `UserId` int(11) unsigned NOT NULL,
  `DepositAddress` varchar(60) NOT NULL,
  `Amount` decimal(18,6) NOT NULL DEFAULT '0.000000',
  PRIMARY KEY (`Id`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `Account_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table BlackjackCard
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BlackjackCard`;

CREATE TABLE `BlackjackCard` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Code` int(11) unsigned NOT NULL,
  `RankCode` int(11) unsigned NOT NULL,
  `RankName` varchar(20) NOT NULL,
  `RankLetter` char(1) NOT NULL,
  `SuitName` int(11) unsigned NOT NULL,
  `SuitCode` int(11) unsigned NOT NULL,
  `PrimaryValue` int(11) unsigned NOT NULL,
  `SecondaryValue` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table BlackjackGame
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BlackjackGame`;

CREATE TABLE `BlackjackGame` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `UserId` int(11) unsigned NOT NULL,
  `StatusCodeId` int(10) unsigned NOT NULL DEFAULT '0',
  `Timestamp` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_userid` (`UserId`),
  KEY `fk_status` (`StatusCodeId`),
  CONSTRAINT `fk_userid` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`),
  CONSTRAINT `fk_status` FOREIGN KEY (`StatusCodeId`) REFERENCES `StatusCode` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table BlackjackGameState
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BlackjackGameState`;

CREATE TABLE `BlackjackGameState` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `UserId` int(11) unsigned NOT NULL,
  `GameId` int(11) unsigned NOT NULL,
  `UserHand` varchar(2000) NOT NULL DEFAULT '[]',
  `DealerHand` varchar(2000) NOT NULL DEFAULT '[]',
  `Description` varchar(200) NOT NULL DEFAULT '',
  `LastCommandId` int(11) unsigned NOT NULL DEFAULT '0',
  `StatusCodeId` int(10) unsigned NOT NULL DEFAULT '0',
  `Timestamp` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_user` (`UserId`),
  KEY `fk_blackjackgame` (`GameId`),
  KEY `fk_lastcommand` (`LastCommandId`),
  KEY `fk_statuscode` (`StatusCodeId`),
  CONSTRAINT `fk_lastcommand` FOREIGN KEY (`LastCommandId`) REFERENCES `Command` (`Id`),
  CONSTRAINT `fk_statuscode` FOREIGN KEY (`StatusCodeId`) REFERENCES `StatusCode` (`Id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`),
  CONSTRAINT `fk_blackjackgame` FOREIGN KEY (`GameId`) REFERENCES `BlackjackGame` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Command
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Command`;

CREATE TABLE `Command` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL DEFAULT 'NONE',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Command` WRITE;
/*!40000 ALTER TABLE `Command` DISABLE KEYS */;

INSERT INTO `Command` (`Id`, `Name`)
VALUES
	(1,'NONE');

/*!40000 ALTER TABLE `Command` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table StatusCode
# ------------------------------------------------------------

DROP TABLE IF EXISTS `StatusCode`;

CREATE TABLE `StatusCode` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Value` varchar(20) NOT NULL DEFAULT 'UNKNOWN',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `StatusCode` WRITE;
/*!40000 ALTER TABLE `StatusCode` DISABLE KEYS */;

INSERT INTO `StatusCode` (`Id`, `Value`)
VALUES
	(1,'UNKNOWN');

/*!40000 ALTER TABLE `StatusCode` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Transaction
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Transaction`;

CREATE TABLE `Transaction` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `AccountId` int(11) unsigned NOT NULL,
  `Amount` decimal(18,6) NOT NULL DEFAULT '0.000000',
  `CommandId` int(11) unsigned NOT NULL DEFAULT '0',
  `Timestamp` datetime NOT NULL,
  `Reason` varchar(2000) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `fk_command` (`CommandId`),
  KEY `fk_account` (`AccountId`),
  CONSTRAINT `fk_account` FOREIGN KEY (`AccountId`) REFERENCES `Account` (`Id`),
  CONSTRAINT `fk_command` FOREIGN KEY (`CommandId`) REFERENCES `Command` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table User
# ------------------------------------------------------------

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Email` varchar(60) NOT NULL DEFAULT '',
  `PasswordHash` varchar(20) NOT NULL DEFAULT '',
  `Timestamp` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
