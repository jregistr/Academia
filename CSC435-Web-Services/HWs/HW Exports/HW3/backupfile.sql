-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: commanders
-- ------------------------------------------------------
-- Server version	5.6.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `battles`
--

DROP TABLE IF EXISTS `battles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `battles` (
  `date_started` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `date_ended` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `winner` enum('BLUE','RED') DEFAULT NULL,
  `turns` int(10) unsigned NOT NULL,
  `knocked_by_blue` int(10) unsigned NOT NULL,
  `lost_by_blue` int(10) unsigned NOT NULL,
  `knocked_by_red` int(10) unsigned NOT NULL,
  `lost_by_red` int(10) unsigned NOT NULL,
  `fired_by_blue` int(10) unsigned DEFAULT NULL,
  `hits_by_blue` int(10) unsigned DEFAULT NULL,
  `fired_by_red` int(10) unsigned DEFAULT NULL,
  `hits_by_red` int(10) unsigned DEFAULT NULL,
  `blue_user` int(10) unsigned NOT NULL,
  `red_user` int(10) unsigned NOT NULL,
  `battle_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`battle_id`),
  KEY `blue_user` (`blue_user`),
  KEY `red_user` (`red_user`),
  CONSTRAINT `blue_user` FOREIGN KEY (`blue_user`) REFERENCES `users` (`user_id`),
  CONSTRAINT `red_user` FOREIGN KEY (`red_user`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battles`
--

LOCK TABLES `battles` WRITE;
/*!40000 ALTER TABLE `battles` DISABLE KEYS */;
INSERT INTO `battles` VALUES ('2015-03-08 14:55:00','2015-03-08 15:55:00','BLUE',20,5,3,3,5,30,16,34,8,2,3,1),('2015-03-08 15:57:00','2015-03-08 16:00:00','RED',34,2,5,5,2,45,11,39,3,2,3,2),('2015-03-10 19:16:12','2015-03-10 19:16:12','BLUE',8,5,2,2,5,15,7,14,4,3,3,3),('2015-03-10 19:16:16','2015-03-10 19:16:16','RED',14,0,5,5,0,8,13,22,5,7,4,4),('2015-03-10 19:16:19','2015-03-10 19:16:19','BLUE',21,5,2,2,5,13,20,15,16,2,3,5),('2015-03-10 19:49:07','2015-03-10 19:49:07','RED',0,0,5,5,2,1,6,18,13,2,6,6),('2015-03-10 19:49:08','2015-03-10 19:49:08','RED',22,0,5,5,3,15,3,16,20,4,4,7),('2015-03-10 19:49:08','2015-03-10 19:49:08','RED',5,0,5,5,3,0,22,27,5,4,7,8),('2015-03-11 00:54:14','2015-03-11 00:54:14','BLUE',22,5,1,1,5,26,16,14,20,6,6,9);
/*!40000 ALTER TABLE `battles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `msg` varchar(250) NOT NULL,
  `time_posted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `poster_user` int(10) unsigned NOT NULL,
  `posted_to_user` int(10) unsigned NOT NULL,
  KEY `poster_user` (`poster_user`),
  KEY `posted_to_user` (`posted_to_user`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`poster_user`) REFERENCES `users` (`user_id`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`posted_to_user`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES ('hello rumble','2015-03-08 20:06:26',2,2),('this is an amazing game rumble','2015-03-08 20:23:23',2,2),('cool story joe','2015-03-08 20:29:12',2,2),('hi there again','2015-03-08 23:09:46',2,2),('hello malibu','2015-03-09 03:36:34',1,2),('testing again','2015-03-09 03:38:41',1,2),('Can we please talk malibu','2015-03-09 03:41:22',2,1),('i am writing this from command','2015-03-09 03:43:02',2,1),('i am writing','2015-03-09 18:51:13',2,2),('i am writing again','2015-03-09 18:52:33',2,2),('i am writing to you','2015-03-09 19:05:00',2,2),('ttttt','2015-03-09 19:07:32',2,2),('ttttt','2015-03-09 19:08:27',2,2),('ttttt','2015-03-09 19:08:38',2,2),('ttttt','2015-03-09 19:08:40',2,2),('ttttt','2015-03-09 19:08:42',2,2),('test jquery','2015-03-09 19:10:07',2,2),('I love posting on my wall','2015-03-09 19:17:37',2,2),('so many tests','2015-03-09 19:20:50',2,2),('too many jquery tests','2015-03-09 19:22:47',2,2),('ooool','2015-03-09 19:24:27',2,2),('oopl','2015-03-09 19:25:10',2,2),('and it works!!!! :P','2015-03-09 19:25:51',2,2),('I\'m a genius','2015-03-09 19:28:29',2,2),('comment','2015-03-10 15:19:09',1,2),('comment','2015-03-10 15:29:19',2,2),('comment','2015-03-10 15:32:28',2,1),('comment','2015-03-10 15:32:47',2,1),('comment','2015-03-10 15:34:45',2,2),('I\'m  a genius who takes forever','2015-03-10 15:37:27',2,2),('jajjajj','2015-03-10 19:30:06',2,2),('hiiiii','2015-03-10 19:46:56',2,2),('dawdawdawdawd','2015-03-10 19:46:59',2,2),('kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk','2015-03-10 19:47:09',2,2),('yyyyy','2015-03-13 17:08:16',2,2),('hi there','2015-03-13 17:08:24',2,2),('hi mali','2015-03-13 17:27:05',1,2),('Malibu hi!','2015-03-13 17:31:09',1,2),('hello malz. how you!','2015-03-13 17:31:34',3,2),('jjj','2015-03-13 17:32:27',2,2),('ooll','2015-03-13 20:00:27',2,2),('ddcc','2015-03-15 23:29:31',2,2),('gggg','2015-03-22 05:43:15',2,2),('dad','2015-03-22 05:48:12',2,2),('sss','2015-03-22 05:48:26',2,2),('lll','2015-03-22 05:50:48',2,2),('zz','2015-03-22 05:52:57',2,2),('zz','2015-03-22 05:53:59',2,2),('mmmm','2015-03-22 13:55:58',2,2),('ggh','2015-03-22 23:43:09',3,2),('vv','2015-03-23 01:35:35',2,2);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customizations`
--

DROP TABLE IF EXISTS `customizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customizations` (
  `profile_img` varchar(200) DEFAULT NULL,
  `background_img` varchar(200) DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `customizations_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customizations`
--

LOCK TABLES `customizations` WRITE;
/*!40000 ALTER TABLE `customizations` DISABLE KEYS */;
/*!40000 ALTER TABLE `customizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `user_id` int(10) unsigned NOT NULL,
  `friend_of` int(10) unsigned NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `user_id` (`user_id`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (2,3,'2015-03-21 19:46:32'),(3,2,'2015-03-21 19:48:04'),(7,2,'2015-03-21 19:48:20'),(5,2,'2015-03-21 19:54:39'),(10,2,'2015-03-21 19:55:44');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `histories`
--

DROP TABLE IF EXISTS `histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `histories` (
  `battles_fought` int(11) NOT NULL,
  `won` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `histories_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `histories`
--

LOCK TABLES `histories` WRITE;
/*!40000 ALTER TABLE `histories` DISABLE KEYS */;
INSERT INTO `histories` VALUES (101,40,900,2);
/*!40000 ALTER TABLE `histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `display_name` varchar(30) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('The Harbinger','malibu','rum',1),('The Mechanized Menace','bandlecity','rumble',2),('super kitty','malz','pass',3),('Jack The ripper','ripperman','passer',4),('The tidal trickster','Fizz','baiting',5),('The Titan of the dephts','Nautilus','swim',6),('Super pow pow','jack','pass',7),('One shot you Cool Guy Cool Guy','veigar','zzzzz',8),('ten shot you','postnerf','zxzx',9),('The poom poom','drpoom','zzz',10),('The man of steel','superman','qwerty',11);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-22 23:54:32
