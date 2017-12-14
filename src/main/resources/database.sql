-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: kanban_hiber
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'egerg','Name');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
INSERT INTO `persistent_logins` VALUES ('qwe','v6uRcOzzvLTnv1snjsh+yQ==','MQ3NToUhx24rWSCpLe6QnA==','2017-10-18 15:46:31');
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `user_lead_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK76fngi71pfr8phbobe5pq0swd` (`company_id`),
  KEY `FK1mr0a62xkuaowwch6mf2xg8cd` (`user_lead_id`),
  CONSTRAINT `FK1mr0a62xkuaowwch6mf2xg8cd` FOREIGN KEY (`user_lead_id`) REFERENCES `user` (`username`),
  CONSTRAINT `FK76fngi71pfr8phbobe5pq0swd` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (4,NULL,NULL,'wefrt',NULL,'qwe');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK82u6w3dkiooc7msaqc1e2cs0v` (`project_id`),
  CONSTRAINT `FK82u6w3dkiooc7msaqc1e2cs0v` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbyy56vice9njgl86752up8120` (`project_id`),
  CONSTRAINT `FKbyy56vice9njgl86752up8120` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_task`
--

DROP TABLE IF EXISTS `tag_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_task` (
  `tag_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  KEY `FKm4b8jkg8t7g2227pjjlu56rli` (`task_id`),
  KEY `FKsn2gvp5n6rliuxcan6ac2c1o3` (`tag_id`),
  CONSTRAINT `FKm4b8jkg8t7g2227pjjlu56rli` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FKsn2gvp5n6rliuxcan6ac2c1o3` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_task`
--

LOCK TABLES `tag_task` WRITE;
/*!40000 ALTER TABLE `tag_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `task_order` int(11) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `creator_id` varchar(80) DEFAULT NULL,
  `executor_id` varchar(80) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk8qrwowg31kx7hp93sru1pdqa` (`project_id`),
  KEY `FKqc1galw66ryn480v0lygu3n4c` (`creator_id`),
  KEY `FK3tpwichpu8cwc6n31jwetnmbx` (`executor_id`),
  KEY `FKhbyh5kk1fpobspep796vg5psq` (`status_id`),
  CONSTRAINT `FK3tpwichpu8cwc6n31jwetnmbx` FOREIGN KEY (`executor_id`) REFERENCES `user` (`username`),
  CONSTRAINT `FKhbyh5kk1fpobspep796vg5psq` FOREIGN KEY (`status_id`) REFERENCES `task_status` (`id`),
  CONSTRAINT `FKk8qrwowg31kx7hp93sru1pdqa` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FKqc1galw66ryn480v0lygu3n4c` FOREIGN KEY (`creator_id`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (2,NULL,NULL,'wegerg',0,'HIGH','ergerg',4,'qwe',NULL,9),(3,NULL,NULL,'wegerg',0,'HIGH','ergerg',4,'qwe',NULL,9),(4,NULL,NULL,'fytvgbhjn',0,'CRITICAL','gbhjnmk',4,'qwe',NULL,9),(5,NULL,NULL,'fytvgbhjn',0,'CRITICAL','gbhjnmk',4,'qwe',NULL,8),(6,NULL,NULL,'fytvgbhjn',0,'CRITICAL','gbhjnmk',4,'qwe',NULL,8),(13,NULL,NULL,'wefwef',0,'NORMAL','wef',4,'qwe',NULL,6);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_history`
--

DROP TABLE IF EXISTS `task_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `editor_username` varchar(80) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9iv9u1py3i80v5y2v4s4qp7dp` (`editor_username`),
  KEY `FKi3rn4f2qwuvfab2um3kwb18wp` (`status_id`),
  KEY `FKer57q2libi1e9njpj6faoxd2i` (`task_id`),
  CONSTRAINT `FK9iv9u1py3i80v5y2v4s4qp7dp` FOREIGN KEY (`editor_username`) REFERENCES `user` (`username`),
  CONSTRAINT `FKer57q2libi1e9njpj6faoxd2i` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FKi3rn4f2qwuvfab2um3kwb18wp` FOREIGN KEY (`status_id`) REFERENCES `task_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_history`
--

LOCK TABLES `task_history` WRITE;
/*!40000 ALTER TABLE `task_history` DISABLE KEYS */;
INSERT INTO `task_history` VALUES (1,'2017-10-18 17:56:07','qwe',6,13),(2,'2017-10-18 18:46:31','qwe',7,13),(3,'2017-10-18 18:46:34','qwe',6,13);
/*!40000 ALTER TABLE `task_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_status`
--

DROP TABLE IF EXISTS `task_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status_order` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKql4n9mmmi1sy2gida356m7kdx` (`project_id`),
  CONSTRAINT `FKql4n9mmmi1sy2gida356m7kdx` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_status`
--

LOCK TABLES `task_status` WRITE;
/*!40000 ALTER TABLE `task_status` DISABLE KEYS */;
INSERT INTO `task_status` VALUES (6,'werwer',0,4),(7,'werwer',1,4),(8,'werwer',2,4),(9,'werwer',3,4);
/*!40000 ALTER TABLE `task_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(80) NOT NULL,
  `can_create_project` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FK2yuxsfrkkrnkn5emoobcnnc3r` (`company_id`),
  CONSTRAINT `FK2yuxsfrkkrnkn5emoobcnnc3r` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('qwe','','zvg96@mail.ru','vlad','zavadski','$2a$10$u4gDAYyMDScY3IA1wEEqiu2Q0eB8mVJXJ5XNvN41beh/VhLOfab/S','qwf',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_project`
--

DROP TABLE IF EXISTS `user_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_project` (
  `project_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`project_id`,`username`),
  KEY `FK3j1yufqrfciibchcyxrt0g1sp` (`username`),
  CONSTRAINT `FK3j1yufqrfciibchcyxrt0g1sp` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  CONSTRAINT `FKocfkr6u2yh3w1qmybs8vxuv1c` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_project`
--

LOCK TABLES `user_project` WRITE;
/*!40000 ALTER TABLE `user_project` DISABLE KEYS */;
INSERT INTO `user_project` VALUES (4,'qwe');
/*!40000 ALTER TABLE `user_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `role_id` int(11) NOT NULL,
  `user_id` varchar(80) NOT NULL,
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`username`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-18 18:49:03
