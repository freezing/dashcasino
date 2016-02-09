# ************************************************************
# Sequel Pro SQL dump
# Version 4500
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 33.33.33.10 (MySQL 5.5.47-0ubuntu0.12.04.1)
# Database: dashcasino
# Generation Time: 2016-02-09 20:09:11 +0000
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
	(422,475,'WalletAddress_1455047495204',90.000000),
	(423,476,'WalletAddress_1455047496140',100.000000),
	(424,477,'WalletAddress_1455047496410',120.000000),
	(425,478,'WalletAddress_1455047496653',25.000000),
	(426,479,'WalletAddress_1455047496783',10.000000),
	(427,480,'WalletAddress_1455047496922',10.000000),
	(428,481,'WalletAddress_1455047497062',40.000000),
	(429,482,'WalletAddress_1455047497385',20.000000),
	(430,483,'WalletAddress_1455047497578',90.000000),
	(431,484,'WalletAddress_1455047497844',90.000000),
	(432,485,'WalletAddress_1455047498008',0.000000),
	(433,486,'WalletAddress_1455047498095',3.000000),
	(434,487,'WalletAddress_1455047498234',6.000000),
	(435,488,'WalletAddress_1455047498354',3.000000);

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
	(5354,0,0,'None','X','None','X',-1,-1,-1),
	(5355,1,1,'Ace','A','Clubs','C',1,11,1),
	(5356,2,2,'Two','2','Clubs','C',1,2,-1),
	(5357,3,3,'Three','3','Clubs','C',1,3,-1),
	(5358,4,4,'Four','4','Clubs','C',1,4,-1),
	(5359,5,5,'Five','5','Clubs','C',1,5,-1),
	(5360,6,6,'Six','6','Clubs','C',1,6,-1),
	(5361,7,7,'Seven','7','Clubs','C',1,7,-1),
	(5362,8,8,'Eight','8','Clubs','C',1,8,-1),
	(5363,9,9,'Nine','9','Clubs','C',1,9,-1),
	(5364,10,10,'Ten','T','Clubs','C',1,10,-1),
	(5365,11,11,'Jack','J','Clubs','C',1,10,-1),
	(5366,12,12,'Queen','Q','Clubs','C',1,10,-1),
	(5367,13,13,'King','K','Clubs','C',1,10,-1),
	(5368,14,1,'Ace','A','Diamonds','D',2,11,1),
	(5369,15,2,'Two','2','Diamonds','D',2,2,-1),
	(5370,16,3,'Three','3','Diamonds','D',2,3,-1),
	(5371,17,4,'Four','4','Diamonds','D',2,4,-1),
	(5372,18,5,'Five','5','Diamonds','D',2,5,-1),
	(5373,19,6,'Six','6','Diamonds','D',2,6,-1),
	(5374,20,7,'Seven','7','Diamonds','D',2,7,-1),
	(5375,21,8,'Eight','8','Diamonds','D',2,8,-1),
	(5376,22,9,'Nine','9','Diamonds','D',2,9,-1),
	(5377,23,10,'Ten','T','Diamonds','D',2,10,-1),
	(5378,24,11,'Jack','J','Diamonds','D',2,10,-1),
	(5379,25,12,'Queen','Q','Diamonds','D',2,10,-1),
	(5380,26,13,'King','K','Diamonds','D',2,10,-1),
	(5381,27,1,'Ace','A','Hearts','H',3,11,1),
	(5382,28,2,'Two','2','Hearts','H',3,2,-1),
	(5383,29,3,'Three','3','Hearts','H',3,3,-1),
	(5384,30,4,'Four','4','Hearts','H',3,4,-1),
	(5385,31,5,'Five','5','Hearts','H',3,5,-1),
	(5386,32,6,'Six','6','Hearts','H',3,6,-1),
	(5387,33,7,'Seven','7','Hearts','H',3,7,-1),
	(5388,34,8,'Eight','8','Hearts','H',3,8,-1),
	(5389,35,9,'Nine','9','Hearts','H',3,9,-1),
	(5390,36,10,'Ten','T','Hearts','H',3,10,-1),
	(5391,37,11,'Jack','J','Hearts','H',3,10,-1),
	(5392,38,12,'Queen','Q','Hearts','H',3,10,-1),
	(5393,39,13,'King','K','Hearts','H',3,10,-1),
	(5394,40,1,'Ace','A','Spaces','S',4,11,1),
	(5395,41,2,'Two','2','Spaces','S',4,2,-1),
	(5396,42,3,'Three','3','Spaces','S',4,3,-1),
	(5397,43,4,'Four','4','Spaces','S',4,4,-1),
	(5398,44,5,'Five','5','Spaces','S',4,5,-1),
	(5399,45,6,'Six','6','Spaces','S',4,6,-1),
	(5400,46,7,'Seven','7','Spaces','S',4,7,-1),
	(5401,47,8,'Eight','8','Spaces','S',4,8,-1),
	(5402,48,9,'Nine','9','Spaces','S',4,9,-1),
	(5403,49,10,'Ten','T','Spaces','S',4,10,-1),
	(5404,50,11,'Jack','J','Spaces','S',4,10,-1),
	(5405,51,12,'Queen','Q','Spaces','S',4,10,-1),
	(5406,52,13,'King','K','Spaces','S',4,10,-1);

/*!40000 ALTER TABLE `BlackjackCard` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table BlackjackDeck
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BlackjackDeck`;

CREATE TABLE `BlackjackDeck` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Order` varchar(1000) NOT NULL DEFAULT '',
  `ServerSeed` int(20) NOT NULL,
  `ClientSeed` varchar(200) NOT NULL DEFAULT '',
  `IsSigned` tinyint(1) NOT NULL DEFAULT '0',
  `Timestamp` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `BlackjackDeck` WRITE;
/*!40000 ALTER TABLE `BlackjackDeck` DISABLE KEYS */;

INSERT INTO `BlackjackDeck` (`Id`, `Order`, `ServerSeed`, `ClientSeed`, `IsSigned`, `Timestamp`)
VALUES
	(276,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:35'),
	(277,'[10,13,11,8,12,3,9,1,2,4,5,6,7,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:36'),
	(278,'[10,13,11,6,12,3,9,1,2,4,5,8,7,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:36'),
	(279,'[1,2,10,4,3,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:36'),
	(280,'[1,11,10,14,2,4,3,5,6,7,8,9,12,13,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:36'),
	(281,'[1,4,8,3,2,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:36'),
	(282,'[1,4,8,3,2,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:37'),
	(283,'[1,4,8,3,2,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:37'),
	(284,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:37'),
	(285,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:37'),
	(286,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:38'),
	(287,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:38'),
	(288,'[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:38'),
	(289,'[10,13,11,8,12,3,9,1,2,4,5,6,7,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52]',0,'clientseed',1,'2016-02-09 19:51:38');

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
	(268,475,0,276,'2016-02-09 19:51:35'),
	(269,476,0,277,'2016-02-09 19:51:36'),
	(270,477,0,278,'2016-02-09 19:51:36'),
	(271,478,0,279,'2016-02-09 19:51:36'),
	(272,479,0,280,'2016-02-09 19:51:36'),
	(273,480,0,281,'2016-02-09 19:51:36'),
	(274,481,0,282,'2016-02-09 19:51:37'),
	(275,482,0,283,'2016-02-09 19:51:37'),
	(276,483,0,284,'2016-02-09 19:51:37'),
	(277,484,0,285,'2016-02-09 19:51:37'),
	(278,485,0,286,'2016-02-09 19:51:38'),
	(279,486,0,287,'2016-02-09 19:51:38'),
	(280,487,0,288,'2016-02-09 19:51:38'),
	(281,488,0,289,'2016-02-09 19:51:38');

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

LOCK TABLES `BlackjackGameState` WRITE;
/*!40000 ALTER TABLE `BlackjackGameState` DISABLE KEYS */;

INSERT INTO `BlackjackGameState` (`Id`, `GameId`, `UserHand`, `DealerHand`, `Description`, `CommandCode`, `Insurance`, `Timestamp`, `StatusCode`)
VALUES
	(578,268,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:36',1),
	(579,269,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:36',1),
	(580,269,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',6,0,'2016-02-09 19:51:36',1),
	(581,269,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:36',1),
	(582,269,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',4,0,'2016-02-09 19:51:36',1),
	(583,269,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:36',1),
	(584,269,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"LOST\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":9,\"rankLetter\":\"9\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":9,\"id\":5363,\"rankName\":\"Nine\",\"primaryValue\":9,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BUSTED\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:36',2),
	(585,269,'{\"hands\":[{\"paymentFinished\":true,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":true,\"outcome\":\"LOST\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":9,\"rankLetter\":\"9\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":9,\"id\":5363,\"rankName\":\"Nine\",\"primaryValue\":9,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BUSTED\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:36',2),
	(586,270,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:36',1),
	(587,270,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',6,0,'2016-02-09 19:51:36',1),
	(588,270,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:36',1),
	(589,270,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',4,0,'2016-02-09 19:51:36',1),
	(590,270,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:36',1),
	(591,270,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":9,\"rankLetter\":\"9\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":9,\"id\":5363,\"rankName\":\"Nine\",\"primaryValue\":9,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BUSTED\",\"money\":10.0}','Nothing for now',4,0,'2016-02-09 19:51:36',2),
	(592,270,'{\"hands\":[{\"paymentFinished\":true,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":12,\"rankLetter\":\"Q\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":12,\"id\":5366,\"rankName\":\"Queen\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":true,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":9,\"rankLetter\":\"9\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":9,\"id\":5363,\"rankName\":\"Nine\",\"primaryValue\":9,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BUSTED\",\"money\":10.0}','Nothing for now',4,0,'2016-02-09 19:51:36',2),
	(593,271,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"WON_BLACKJACK\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BLACKJACK\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:36',2),
	(594,271,'{\"hands\":[{\"paymentFinished\":true,\"outcome\":\"WON_BLACKJACK\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BLACKJACK\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:36',2),
	(595,272,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"TIE\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BLACKJACK\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"D\",\"secondaryValue\":1,\"code\":14,\"id\":5368,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":2,\"suitName\":\"Diamonds\"}],\"status\":\"BLACKJACK\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:36',2),
	(596,272,'{\"hands\":[{\"paymentFinished\":true,\"outcome\":\"TIE\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BLACKJACK\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"D\",\"secondaryValue\":1,\"code\":14,\"id\":5368,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":2,\"suitName\":\"Diamonds\"}],\"status\":\"BLACKJACK\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:36',2),
	(597,273,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:36',1),
	(598,273,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:36',1),
	(599,274,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:37',1),
	(600,274,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"DOUBLE-DOWN\",\"money\":20.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:37',2),
	(601,274,'{\"hands\":[{\"paymentFinished\":true,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"DOUBLE-DOWN\",\"money\":20.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:37',2),
	(602,275,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:37',1),
	(603,275,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:37',1),
	(604,275,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}','Nothing for now',4,0,'2016-02-09 19:51:37',2),
	(605,275,'{\"hands\":[{\"paymentFinished\":true,\"outcome\":\"WON\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":8,\"rankLetter\":\"8\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":8,\"id\":5362,\"rankName\":\"Eight\",\"primaryValue\":8,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}','Nothing for now',4,0,'2016-02-09 19:51:37',2),
	(606,276,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:37',1),
	(607,276,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:37',1),
	(608,276,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:37',1),
	(609,276,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"LOST\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":7,\"rankLetter\":\"7\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":7,\"id\":5361,\"rankName\":\"Seven\",\"primaryValue\":7,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BUSTED\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:37',2),
	(610,276,'{\"hands\":[{\"paymentFinished\":true,\"outcome\":\"LOST\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":7,\"rankLetter\":\"7\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":7,\"id\":5361,\"rankName\":\"Seven\",\"primaryValue\":7,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"BUSTED\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',3,0,'2016-02-09 19:51:37',2),
	(611,277,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":10.0}','Nothing for now',2,0,'2016-02-09 19:51:37',1),
	(612,277,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"LOST\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}','Nothing for now',4,0,'2016-02-09 19:51:37',2),
	(613,277,'{\"hands\":[{\"paymentFinished\":true,\"outcome\":\"LOST\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":4,\"rankLetter\":\"4\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":4,\"id\":5358,\"rankName\":\"Four\",\"primaryValue\":4,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":5,\"rankLetter\":\"5\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":5,\"id\":5359,\"rankName\":\"Five\",\"primaryValue\":5,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":6,\"rankLetter\":\"6\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":6,\"id\":5360,\"rankName\":\"Six\",\"primaryValue\":6,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"STANDING\",\"money\":10.0}','Nothing for now',4,0,'2016-02-09 19:51:37',2),
	(614,279,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":7.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":7.0}','Nothing for now',2,0,'2016-02-09 19:51:38',1),
	(615,280,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":1,\"rankLetter\":\"A\",\"suitLetter\":\"C\",\"secondaryValue\":1,\"code\":1,\"id\":5355,\"rankName\":\"Ace\",\"primaryValue\":11,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":3,\"rankLetter\":\"3\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":3,\"id\":5357,\"rankName\":\"Three\",\"primaryValue\":3,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":4.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":2,\"rankLetter\":\"2\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":2,\"id\":5356,\"rankName\":\"Two\",\"primaryValue\":2,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":4.0}','Nothing for now',2,0,'2016-02-09 19:51:38',1),
	(616,281,'{\"hands\":[{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":10,\"rankLetter\":\"T\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":10,\"id\":5364,\"rankName\":\"Ten\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":11,\"rankLetter\":\"J\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":11,\"id\":5365,\"rankName\":\"Jack\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"}],\"status\":\"OPEN\",\"money\":7.0},{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[],\"status\":\"EMPTY\",\"money\":0.0}]}','{\"paymentFinished\":false,\"outcome\":\"PENDING\",\"cards\":[{\"rankCode\":13,\"rankLetter\":\"K\",\"suitLetter\":\"C\",\"secondaryValue\":-1,\"code\":13,\"id\":5367,\"rankName\":\"King\",\"primaryValue\":10,\"suitCode\":1,\"suitName\":\"Clubs\"},{\"rankCode\":0,\"rankLetter\":\"X\",\"suitLetter\":\"X\",\"secondaryValue\":-1,\"code\":0,\"id\":5354,\"rankName\":\"None\",\"primaryValue\":-1,\"suitCode\":-1,\"suitName\":\"None\"}],\"status\":\"DEALER\",\"money\":7.0}','Nothing for now',2,0,'2016-02-09 19:51:38',1);

/*!40000 ALTER TABLE `BlackjackGameState` ENABLE KEYS */;
UNLOCK TABLES;


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
	(645,'EXTERNAL_WITHDRAWAL',0),
	(646,'EXTERNAL_DEPOSIT',1),
	(647,'BLACKJACK_BET',2),
	(648,'BLACKJACK_HIT',3),
	(649,'BLACKJACK_STAND',4),
	(650,'BLACKJACK_DOUBLEDOWN',5),
	(651,'BLACKJACK_SPLIT',6);

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
	(402,'UNKNOWN',0),
	(403,'BLACKJACK_ROUND_RUNNING',1),
	(404,'BLACKJACK_ROUND_FINISHED',2),
	(405,'BLACKJACK_GAME_FINISHED',3);

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
	(1504,422,100.000000,0,0,'2016-02-09 19:51:35','{Description: Lets play some blackjack}'),
	(1505,422,100.000000,0,1,'2016-02-09 19:51:35','{Description: Lets play some blackjack}'),
	(1506,422,-10.000000,0,0,'2016-02-09 19:51:35','{command: BET, amount: 10.0}'),
	(1507,422,-10.000000,0,1,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1508,423,100.000000,0,0,'2016-02-09 19:51:36','{Description: Lets play some blackjack}'),
	(1509,423,100.000000,0,1,'2016-02-09 19:51:36','{Description: Lets play some blackjack}'),
	(1510,423,-10.000000,0,0,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1511,423,-10.000000,0,1,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1512,423,-10.000000,0,0,'2016-02-09 19:51:36','{description: SPLIT}'),
	(1513,423,-10.000000,0,1,'2016-02-09 19:51:36','{description: SPLIT}'),
	(1514,423,20.000000,0,0,'2016-02-09 19:51:36','{description: User has won}'),
	(1515,423,20.000000,0,1,'2016-02-09 19:51:36','{description: User has won}'),
	(1516,423,0.000000,0,0,'2016-02-09 19:51:36','{description: User has won}'),
	(1517,423,0.000000,0,1,'2016-02-09 19:51:36','{description: User has won}'),
	(1518,424,100.000000,0,0,'2016-02-09 19:51:36','{Description: Lets play some blackjack}'),
	(1519,424,100.000000,0,1,'2016-02-09 19:51:36','{Description: Lets play some blackjack}'),
	(1520,424,-10.000000,0,0,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1521,424,-10.000000,0,1,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1522,424,-10.000000,0,0,'2016-02-09 19:51:36','{description: SPLIT}'),
	(1523,424,-10.000000,0,1,'2016-02-09 19:51:36','{description: SPLIT}'),
	(1524,424,20.000000,0,0,'2016-02-09 19:51:36','{description: User has won}'),
	(1525,424,20.000000,0,1,'2016-02-09 19:51:36','{description: User has won}'),
	(1526,424,20.000000,0,0,'2016-02-09 19:51:36','{description: User has won}'),
	(1527,424,20.000000,0,1,'2016-02-09 19:51:36','{description: User has won}'),
	(1528,425,10.000000,0,0,'2016-02-09 19:51:36','Wanna play some BJ!!!'),
	(1529,425,10.000000,0,1,'2016-02-09 19:51:36','Wanna play some BJ!!!'),
	(1530,425,-10.000000,0,0,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1531,425,-10.000000,0,1,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1532,425,25.000000,0,0,'2016-02-09 19:51:36','{description: User has won}'),
	(1533,425,25.000000,0,1,'2016-02-09 19:51:36','{description: User has won}'),
	(1534,426,10.000000,0,0,'2016-02-09 19:51:36','Wanna play some BJ!!!'),
	(1535,426,10.000000,0,1,'2016-02-09 19:51:36','Wanna play some BJ!!!'),
	(1536,426,-10.000000,0,0,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1537,426,-10.000000,0,1,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1538,426,10.000000,0,0,'2016-02-09 19:51:36','{description: User has won}'),
	(1539,426,10.000000,0,1,'2016-02-09 19:51:36','{description: User has won}'),
	(1540,427,20.000000,0,0,'2016-02-09 19:51:36','Wanna play some BJ!!!'),
	(1541,427,20.000000,0,1,'2016-02-09 19:51:36','Wanna play some BJ!!!'),
	(1542,427,-10.000000,0,0,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1543,427,-10.000000,0,1,'2016-02-09 19:51:36','{command: BET, amount: 10.0}'),
	(1544,428,20.000000,0,0,'2016-02-09 19:51:37','Wanna play some BJ!!!'),
	(1545,428,20.000000,0,1,'2016-02-09 19:51:37','Wanna play some BJ!!!'),
	(1546,428,-10.000000,0,0,'2016-02-09 19:51:37','{command: BET, amount: 10.0}'),
	(1547,428,-10.000000,0,1,'2016-02-09 19:51:37','{command: BET, amount: 10.0}'),
	(1548,428,-10.000000,0,0,'2016-02-09 19:51:37','{description: DOUBLEDOWN}'),
	(1549,428,-10.000000,0,1,'2016-02-09 19:51:37','{description: DOUBLEDOWN}'),
	(1550,428,40.000000,0,0,'2016-02-09 19:51:37','{description: User has won}'),
	(1551,428,40.000000,0,1,'2016-02-09 19:51:37','{description: User has won}'),
	(1552,429,10.000000,0,0,'2016-02-09 19:51:37','Wanna play some BJ!!!'),
	(1553,429,10.000000,0,1,'2016-02-09 19:51:37','Wanna play some BJ!!!'),
	(1554,429,-10.000000,0,0,'2016-02-09 19:51:37','{command: BET, amount: 10.0}'),
	(1555,429,-10.000000,0,1,'2016-02-09 19:51:37','{command: BET, amount: 10.0}'),
	(1556,429,20.000000,0,0,'2016-02-09 19:51:37','{description: User has won}'),
	(1557,429,20.000000,0,1,'2016-02-09 19:51:37','{description: User has won}'),
	(1558,430,100.000000,0,0,'2016-02-09 19:51:37','{Description: Lets play some blackjack}'),
	(1559,430,100.000000,0,1,'2016-02-09 19:51:37','{Description: Lets play some blackjack}'),
	(1560,430,-10.000000,0,0,'2016-02-09 19:51:37','{command: BET, amount: 10.0}'),
	(1561,430,-10.000000,0,1,'2016-02-09 19:51:37','{command: BET, amount: 10.0}'),
	(1562,430,0.000000,0,0,'2016-02-09 19:51:37','{description: User has won}'),
	(1563,430,0.000000,0,1,'2016-02-09 19:51:37','{description: User has won}'),
	(1564,431,100.000000,0,0,'2016-02-09 19:51:37','{Description: Lets play some blackjack}'),
	(1565,431,100.000000,0,1,'2016-02-09 19:51:37','{Description: Lets play some blackjack}'),
	(1566,431,-10.000000,0,0,'2016-02-09 19:51:37','{command: BET, amount: 10.0}'),
	(1567,431,-10.000000,0,1,'2016-02-09 19:51:37','{command: BET, amount: 10.0}'),
	(1568,431,0.000000,0,0,'2016-02-09 19:51:37','{description: User has won}'),
	(1569,431,0.000000,0,1,'2016-02-09 19:51:37','{description: User has won}'),
	(1570,433,10.000000,0,0,'2016-02-09 19:51:38',''),
	(1571,433,10.000000,0,1,'2016-02-09 19:51:38',''),
	(1572,433,-7.000000,0,0,'2016-02-09 19:51:38','{command: BET, amount: 7.0}'),
	(1573,433,-7.000000,0,1,'2016-02-09 19:51:38','{command: BET, amount: 7.0}'),
	(1574,434,10.000000,0,0,'2016-02-09 19:51:38',''),
	(1575,434,10.000000,0,1,'2016-02-09 19:51:38',''),
	(1576,434,-4.000000,0,0,'2016-02-09 19:51:38','{command: BET, amount: 4.0}'),
	(1577,434,-4.000000,0,1,'2016-02-09 19:51:38','{command: BET, amount: 4.0}'),
	(1578,435,10.000000,0,0,'2016-02-09 19:51:38',''),
	(1579,435,10.000000,0,1,'2016-02-09 19:51:38',''),
	(1580,435,-7.000000,0,0,'2016-02-09 19:51:38','{command: BET, amount: 7.0}'),
	(1581,435,-7.000000,0,1,'2016-02-09 19:51:38','{command: BET, amount: 7.0}');

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
	(475,'blackjackservicetest_startstate@gmail.com','$2a$10$95iLzXbK33mE.BneRRTtI.ILyCL1KlsWJa0xYwJfpcF4nxMQcRxZ6','2016-02-09 19:51:35'),
	(476,'blackjackservicetest_split_win_busted@gmail.com','$2a$10$/20w71Nq3gEv3PwE13mPdORN2fPEdETO38p7LVuPDfdUTKOt8VNaa','2016-02-09 19:51:36'),
	(477,'blackjackservicetest_split_win_win@gmail.com','$2a$10$HEN0Kp56BAY2NaHquhku.e0ETOBG8qNF9g2HcNxAVDEfm7DuTDs1G','2016-02-09 19:51:36'),
	(478,'blackjackservicetest_userwinsafteronehit@gmail.com','$2a$10$4itoOxG.78jLNqbgmI1mhO/w.HrmHX8xD6bfTpwGjvx5DNdrJuLBm','2016-02-09 19:51:36'),
	(479,'blackjackservicetest_blackjacktie@gmail.com','$2a$10$gyMjnCifO8G5gSVymKzdJe4CnggVBODwivDsIrNAcMqkDvJqXjwyK','2016-02-09 19:51:36'),
	(480,'blackjackservicetest_nodoubledownafterhit@gmail.com','$2a$10$d2iUVT2AQxS96HoFOgK.q.UZmaEpzhuvXjWaUYIebiQ13rzSjCB4W','2016-02-09 19:51:36'),
	(481,'blackjackservicetest_userwinsafterdoubledown@gmail.com','$2a$10$V2xGyO.huMC8wKjbMASYG.YKeRr9Ip1os0.KHIi1MbzDTm7/Fxqja','2016-02-09 19:51:37'),
	(482,'blackjackservicetest_userearnsmoneyafterwin@gmail.com','$2a$10$Qy/J41LI6Ieb4rsh/G/8deIKw2GVYdILUift01hF8zs24enVgkOg.','2016-02-09 19:51:37'),
	(483,'blackjackservicetest_longsanity@gmail.com','$2a$10$YlJCMmo1JFFSWiOKWV65s.QLIDB99yvwUwGUMIMrKIQl2N81cJKJu','2016-02-09 19:51:37'),
	(484,'blackjackservicetest_firststand@gmail.com','$2a$10$oLarUC3jcy/TwECyu3M4..IxihTOxsTjNS7pcVfFiO3drQ.YpY8Z2','2016-02-09 19:51:37'),
	(485,'blackjackservicetest_betnomoney@gmail.com','$2a$10$rn7061FWC5rzoqcnDqBsA.SEK6AFPkF0F7birK4AWVDs2bxFBk85y','2016-02-09 19:51:38'),
	(486,'blacjackservicetest_doubledownnomoney@gmail.com','$2a$10$vQFswWA9/SWM2fml09KBpO.3wOJby8ZVt.H6EEAKLGDAXmddcTJh6','2016-02-09 19:51:38'),
	(487,'blackjackservicetest_cantspliteception@gmail.com','$2a$10$cFtSyMiwRbpiM5VVxXQTlOFeK72RUVnpP/.ZlXIHmkc3P5RVx3y6u','2016-02-09 19:51:38'),
	(488,'blackjackservicetest_cantsplitnomoney@gmail.com','$2a$10$EP6MnoCK8/saUF1QigywN.88c/OVg/X7oATFTJAXP8UdXdE5ZAcma','2016-02-09 19:51:38');

/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
