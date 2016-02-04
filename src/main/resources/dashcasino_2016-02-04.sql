# ************************************************************
# Sequel Pro SQL dump
# Version 4500
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 33.33.33.10 (MySQL 5.5.47-0ubuntu0.12.04.1)
# Database: dashcasino
# Generation Time: 2016-02-04 21:54:40 +0000
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
	(147,193,'WalletAddress_1454622813121',100.000000),
	(148,194,'WalletAddress_1454622813769',0.000000);

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

LOCK TABLES `BlackjackCard` WRITE;
/*!40000 ALTER TABLE `BlackjackCard` DISABLE KEYS */;

INSERT INTO `BlackjackCard` (`Id`, `Code`, `RankCode`, `RankName`, `RankLetter`, `SuitName`, `SuitLetter`, `SuitCode`, `PrimaryValue`, `SecondaryValue`)
VALUES
	(1,1,1,'Ace','A','Clubs','C',1,11,1),
	(2,2,2,'Two','2','Clubs','C',1,2,-1),
	(3,3,3,'Three','3','Clubs','C',1,3,-1),
	(4,4,4,'Four','4','Clubs','C',1,4,-1),
	(5,5,5,'Five','5','Clubs','C',1,5,-1),
	(6,6,6,'Six','6','Clubs','C',1,6,-1),
	(7,7,7,'Seven','7','Clubs','C',1,7,-1),
	(8,8,8,'Eight','8','Clubs','C',1,8,-1),
	(9,9,9,'Nine','9','Clubs','C',1,9,-1),
	(10,10,10,'Ten','T','Clubs','C',1,10,-1),
	(11,11,11,'Jack','J','Clubs','C',1,10,-1),
	(12,12,12,'Queen','Q','Clubs','C',1,10,-1),
	(13,13,13,'King','K','Clubs','C',1,10,-1),
	(14,14,1,'Ace','A','Diamonds','D',1,11,1),
	(15,15,2,'Two','2','Diamonds','D',1,2,-1),
	(16,16,3,'Three','3','Diamonds','D',1,3,-1),
	(17,17,4,'Four','4','Diamonds','D',1,4,-1),
	(18,18,5,'Five','5','Diamonds','D',1,5,-1),
	(19,19,6,'Six','6','Diamonds','D',1,6,-1),
	(20,20,7,'Seven','7','Diamonds','D',1,7,-1),
	(21,21,8,'Eight','8','Diamonds','D',1,8,-1),
	(22,22,9,'Nine','9','Diamonds','D',1,9,-1),
	(23,23,10,'Ten','T','Diamonds','D',1,10,-1),
	(24,24,11,'Jack','J','Diamonds','D',1,10,-1),
	(25,25,12,'Queen','Q','Diamonds','D',1,10,-1),
	(26,26,13,'King','K','Diamonds','D',1,10,-1),
	(27,27,1,'Ace','A','Hearts','H',1,11,1),
	(28,28,2,'Two','2','Hearts','H',1,2,-1),
	(29,29,3,'Three','3','Hearts','H',1,3,-1),
	(30,30,4,'Four','4','Hearts','H',1,4,-1),
	(31,31,5,'Five','5','Hearts','H',1,5,-1),
	(32,32,6,'Six','6','Hearts','H',1,6,-1),
	(33,33,7,'Seven','7','Hearts','H',1,7,-1),
	(34,34,8,'Eight','8','Hearts','H',1,8,-1),
	(35,35,9,'Nine','9','Hearts','H',1,9,-1),
	(36,36,10,'Ten','T','Hearts','H',1,10,-1),
	(37,37,11,'Jack','J','Hearts','H',1,10,-1),
	(38,38,12,'Queen','Q','Hearts','H',1,10,-1),
	(39,39,13,'King','K','Hearts','H',1,10,-1),
	(40,40,1,'Ace','A','Spaces','S',1,11,1),
	(41,41,2,'Two','2','Spaces','S',1,2,-1),
	(42,42,3,'Three','3','Spaces','S',1,3,-1),
	(43,43,4,'Four','4','Spaces','S',1,4,-1),
	(44,44,5,'Five','5','Spaces','S',1,5,-1),
	(45,45,6,'Six','6','Spaces','S',1,6,-1),
	(46,46,7,'Seven','7','Spaces','S',1,7,-1),
	(47,47,8,'Eight','8','Spaces','S',1,8,-1),
	(48,48,9,'Nine','9','Spaces','S',1,9,-1),
	(49,49,10,'Ten','T','Spaces','S',1,10,-1),
	(50,50,11,'Jack','J','Spaces','S',1,10,-1),
	(51,51,12,'Queen','Q','Spaces','S',1,10,-1),
	(52,52,13,'King','K','Spaces','S',1,10,-1),
	(53,0,0,'None','X','None','X',-1,-1,-1);

/*!40000 ALTER TABLE `BlackjackCard` ENABLE KEYS */;
UNLOCK TABLES;


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
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `BlackjackDeck` WRITE;
/*!40000 ALTER TABLE `BlackjackDeck` DISABLE KEYS */;

INSERT INTO `BlackjackDeck` (`Id`, `Order`, `ServerSeed`, `ClientSeed`, `IsSigned`, `Timestamp`)
VALUES
	(18,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]','serverseed','clientseed',1,'2016-02-04 21:53:33'),
	(19,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]','serverseed','clientseed',1,'2016-02-04 21:53:33');

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

LOCK TABLES `BlackjackGame` WRITE;
/*!40000 ALTER TABLE `BlackjackGame` DISABLE KEYS */;

INSERT INTO `BlackjackGame` (`Id`, `UserId`, `StatusCodeId`, `BlackjackDeckId`, `Timestamp`)
VALUES
	(10,193,0,18,'2016-02-04 21:53:33'),
	(11,194,0,19,'2016-02-04 21:53:33');

/*!40000 ALTER TABLE `BlackjackGame` ENABLE KEYS */;
UNLOCK TABLES;


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
	(2,'EXTERNAL_WITHDRAWAL',0),
	(3,'EXTERNAL_DEPOSIT',1),
	(4,'BLACKJACK_BET',2),
	(5,'BLACKJACK_HIT',3),
	(6,'BLACKJACK_STAND',4),
	(7,'BLACKJACK_DOUBLEDOWN',5);

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

LOCK TABLES `StatusCode` WRITE;
/*!40000 ALTER TABLE `StatusCode` DISABLE KEYS */;

INSERT INTO `StatusCode` (`Id`, `Value`, `Code`)
VALUES
	(2,'UNKNOWN',0),
	(3,'BLACKJACK_GAME_RUNNING',1),
	(4,'BLACKJACK_GAME_FINISHED',2);

/*!40000 ALTER TABLE `StatusCode` ENABLE KEYS */;
UNLOCK TABLES;


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
	(262,147,100.000000,0,0,'2016-02-04 21:53:33','{Description: Lets play some blackjack}'),
	(263,147,100.000000,0,1,'2016-02-04 21:53:33','{Description: Lets play some blackjack}');

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
	(193,'blackjackservicetest1@gmail.com','$2a$10$VJZ5kzf5LJ8Dx1W3zvlN3OC.rxETvp5uKNegMt8AvujMqEI37mpSm','2016-02-04 21:53:33'),
	(194,'blackjackservicetest2@gmail.com','$2a$10$04BTFu6Jaia3H9uECnBRsu8GyP1KG7/TMWoYA.G0/mdinzaY2Suq2','2016-02-04 21:53:33');

/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
