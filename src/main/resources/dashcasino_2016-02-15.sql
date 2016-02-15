# ************************************************************
# Sequel Pro SQL dump
# Version 4500
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 33.33.33.10 (MySQL 5.5.47-0ubuntu0.12.04.1)
# Database: dashcasino
# Generation Time: 2016-02-15 23:12:11 +0000
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

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;

INSERT INTO `Account` (`Id`, `UserId`, `DepositAddress`, `Amount`)
VALUES
	(458,515,'WalletAddress_1455577711768',100.000000);

/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table BlackjackCard
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BlackjackCard`;

CREATE TABLE `BlackjackCard` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Code` int(11) NOT NULL,
  `RankCode` int(11) NOT NULL,
  `RankName` varchar(20) NOT NULL,
  `RankLetter` varchar(5) NOT NULL DEFAULT '',
  `SuitName` varchar(11) NOT NULL DEFAULT '',
  `SuitLetter` varchar(5) NOT NULL DEFAULT '',
  `SuitCode` int(11) NOT NULL,
  `PrimaryValue` int(11) NOT NULL,
  `SecondaryValue` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table BlackjackDeck
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BlackjackDeck`;

CREATE TABLE `BlackjackDeck` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Order` varchar(1000) NOT NULL DEFAULT '',
  `ServerSeed` varchar(200) NOT NULL DEFAULT '',
  `ClientSeed` varchar(200) NOT NULL DEFAULT '',
  `IsSigned` tinyint(1) NOT NULL DEFAULT '0',
  `Timestamp` datetime NOT NULL,
  `ServerSecret` varchar(200) NOT NULL DEFAULT '',
  `FinalSecret` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `BlackjackDeck` WRITE;
/*!40000 ALTER TABLE `BlackjackDeck` DISABLE KEYS */;

INSERT INTO `BlackjackDeck` (`Id`, `Order`, `ServerSeed`, `ClientSeed`, `IsSigned`, `Timestamp`, `ServerSecret`, `FinalSecret`)
VALUES
	(290,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,... (148)','2147483647','clientseed',1,'2016-02-15 23:09:20','','');

/*!40000 ALTER TABLE `BlackjackDeck` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table BlackjackGame
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BlackjackGame`;

CREATE TABLE `BlackjackGame` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `UserId` int(11) unsigned NOT NULL,
  `StatusCodeId` int(10) unsigned NOT NULL DEFAULT '0',
  `BlackjackDeckId` int(11) unsigned NOT NULL,
  `Timestamp` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_userid` (`UserId`),
  KEY `fk_blackjackdeckid` (`BlackjackDeckId`),
  CONSTRAINT `fk_blackjackdeckid` FOREIGN KEY (`BlackjackDeckId`) REFERENCES `BlackjackDeck` (`id`),
  CONSTRAINT `fk_userid` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table BlackjackGameState
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BlackjackGameState`;

CREATE TABLE `BlackjackGameState` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `GameId` int(11) unsigned NOT NULL,
  `UserHand` varchar(2000) NOT NULL DEFAULT '[]',
  `DealerHand` varchar(2000) NOT NULL DEFAULT '[]',
  `Description` varchar(200) NOT NULL DEFAULT '',
  `CommandCode` int(11) unsigned NOT NULL DEFAULT '0',
  `Insurance` int(11) unsigned DEFAULT '0',
  `Timestamp` datetime NOT NULL,
  `StatusCode` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_blackjackgame` (`GameId`),
  KEY `fk_commandcode` (`CommandCode`),
  CONSTRAINT `fk_blackjackgame` FOREIGN KEY (`GameId`) REFERENCES `BlackjackGame` (`Id`),
  CONSTRAINT `fk_commandcode` FOREIGN KEY (`CommandCode`) REFERENCES `Command` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Command
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Command`;

CREATE TABLE `Command` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL DEFAULT 'NONE',
  `Code` int(11) unsigned NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `CodeKey` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Command` WRITE;
/*!40000 ALTER TABLE `Command` DISABLE KEYS */;

INSERT INTO `Command` (`Id`, `Name`, `Code`)
VALUES
	(666,'EXTERNAL_WITHDRAWAL',0),
	(667,'EXTERNAL_DEPOSIT',1),
	(668,'BLACKJACK_BET',2),
	(669,'BLACKJACK_HIT',3),
	(670,'BLACKJACK_STAND',4),
	(671,'BLACKJACK_DOUBLEDOWN',5),
	(672,'BLACKJACK_SPLIT',6);

/*!40000 ALTER TABLE `Command` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table StatusCode
# ------------------------------------------------------------

DROP TABLE IF EXISTS `StatusCode`;

CREATE TABLE `StatusCode` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Value` varchar(50) NOT NULL DEFAULT 'UNKNOWN',
  `Code` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `key_code` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Transaction
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Transaction`;

CREATE TABLE `Transaction` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `AccountId` int(11) unsigned NOT NULL,
  `Amount` decimal(18,6) NOT NULL DEFAULT '0.000000',
  `CommandCode` int(11) unsigned NOT NULL DEFAULT '0',
  `Confirmed` int(1) NOT NULL DEFAULT '0',
  `Timestamp` datetime NOT NULL,
  `Reason` varchar(2000) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `fk_command` (`CommandCode`),
  KEY `fk_account` (`AccountId`),
  CONSTRAINT `fk_account` FOREIGN KEY (`AccountId`) REFERENCES `Account` (`Id`),
  CONSTRAINT `fk_command` FOREIGN KEY (`CommandCode`) REFERENCES `Command` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Transaction` WRITE;
/*!40000 ALTER TABLE `Transaction` DISABLE KEYS */;

INSERT INTO `Transaction` (`Id`, `AccountId`, `Amount`, `CommandCode`, `Confirmed`, `Timestamp`, `Reason`)
VALUES
	(1612,458,100.000000,0,0,'2016-02-15 23:08:31','{Description: Lets play some blackjack}'),
	(1613,458,100.000000,0,1,'2016-02-15 23:08:31','{Description: Lets play some blackjack}');

/*!40000 ALTER TABLE `Transaction` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table User
# ------------------------------------------------------------

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Email` varchar(60) NOT NULL DEFAULT '',
  `PasswordHash` varchar(120) NOT NULL DEFAULT '',
  `Timestamp` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;

INSERT INTO `User` (`Id`, `Email`, `PasswordHash`, `Timestamp`)
VALUES
	(515,'blackjackservicetest_startstate@gmail.com','$2a$10$oSTk122pazWz6C8mcmsQvunjatDQXt9.OBuSQqqxEZe3DM9zNGpKe','2016-02-15 23:08:31');

/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
