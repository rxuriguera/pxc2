-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.75-0ubuntu10.3


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema speerker
--

CREATE DATABASE IF NOT EXISTS speerker;
USE speerker;

--
-- Definition of table `speerker`.`plays`
--

DROP TABLE IF EXISTS `speerker`.`plays`;
CREATE TABLE  `speerker`.`plays` (
  `id` int(11) NOT NULL auto_increment,
  `timeStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `user` int(11) NOT NULL,
  `song` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `speerker`.`plays`
--

/*!40000 ALTER TABLE `plays` DISABLE KEYS */;
LOCK TABLES `plays` WRITE;
INSERT INTO `speerker`.`plays` VALUES  (1,'2010-05-05 01:54:25',1,2),
 (2,'2010-05-04 01:53:25',1,1),
 (3,'2010-05-04 01:52:25',2,4),
 (4,'2010-05-02 01:57:25',1,3),
 (5,'2010-05-05 00:52:22',2,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `plays` ENABLE KEYS */;


--
-- Definition of table `speerker`.`songs`
--

DROP TABLE IF EXISTS `speerker`.`songs`;
CREATE TABLE  `speerker`.`songs` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(150) NOT NULL,
  `artist` varchar(150) NOT NULL,
  `album` varchar(150) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `speerker`.`songs`
--

/*!40000 ALTER TABLE `songs` DISABLE KEYS */;
LOCK TABLES `songs` WRITE;
INSERT INTO `speerker`.`songs` VALUES  (1,'Blister In The Sun','Violent Femmes','Violent Femmes'),
 (2,'Swim','Oh No Ono','Eggs'),
 (3,'The Balcony','The Rumour Said Fire','The Life and Death of a Male Body'),
 (4,'After Hours','The Velvet Underground','The Velvet Underground');
UNLOCK TABLES;
/*!40000 ALTER TABLE `songs` ENABLE KEYS */;


--
-- Definition of table `speerker`.`users`
--

DROP TABLE IF EXISTS `speerker`.`users`;
CREATE TABLE  `speerker`.`users` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  PRIMARY KEY  (`username`),
  KEY `ID` USING BTREE (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='Users';

--
-- Dumping data for table `speerker`.`users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
LOCK TABLES `users` WRITE;
INSERT INTO `speerker`.`users` VALUES  (1,'user2','pass2','Test','User 2'),
 (2,'user1','pass1','Test','User'),
 (4,'mittens','love \'em','First','Last'),
 (5,'mittens2','love \'em','First','Last'),
 (6,'mittens3','love \'em','First','Last');
UNLOCK TABLES;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
