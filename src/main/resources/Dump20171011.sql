-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: kanban_board
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.22-MariaDB

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
-- Table structure for table `attachement`
--

DROP TABLE IF EXISTS `attachement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachement` (
  `at_id` int(11) NOT NULL,
  `at_url` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL,
  `at_date` datetime NOT NULL,
  `at_size` int(20) NOT NULL,
  `at_task_id` int(11) NOT NULL,
  `at_owner` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`at_id`),
  KEY `fk_attachement_1_idx` (`at_owner`),
  KEY `fk_attachement_2_idx` (`at_task_id`),
  CONSTRAINT `fk_attachement_1` FOREIGN KEY (`at_owner`) REFERENCES `user` (`us_username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_attachement_2` FOREIGN KEY (`at_task_id`) REFERENCES `task` (`ta_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachement`
--

LOCK TABLES `attachement` WRITE;
/*!40000 ALTER TABLE `attachement` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `change_status_permission`
--

DROP TABLE IF EXISTS `change_status_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `change_status_permission` (
  `csp_perm_id` int(11) NOT NULL,
  `csp_status_from` int(11) NOT NULL,
  `csp_status_to` int(11) NOT NULL,
  PRIMARY KEY (`csp_perm_id`),
  KEY `fk_change_status_permission_2_idx` (`csp_status_from`),
  KEY `fk_change_status_permission_3_idx` (`csp_status_to`),
  CONSTRAINT `fk_change_status_permission_1` FOREIGN KEY (`csp_perm_id`) REFERENCES `permission` (`pe_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_change_status_permission_2` FOREIGN KEY (`csp_status_from`) REFERENCES `task_status` (`ts_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_change_status_permission_3` FOREIGN KEY (`csp_status_to`) REFERENCES `task_status` (`ts_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `change_status_permission`
--

LOCK TABLES `change_status_permission` WRITE;
/*!40000 ALTER TABLE `change_status_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `change_status_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `co_id` int(11) NOT NULL,
  `co_task_id` int(11) NOT NULL,
  `co_creator` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `co_date` datetime NOT NULL,
  `co_content` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`co_id`),
  KEY `fk_comment_1_idx` (`co_creator`),
  KEY `fk_comment_2_idx` (`co_task_id`),
  CONSTRAINT `fk_comment_1` FOREIGN KEY (`co_creator`) REFERENCES `user` (`us_username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_2` FOREIGN KEY (`co_task_id`) REFERENCES `task` (`ta_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `co_id` int(11) NOT NULL,
  `co_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `co_logo` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `co_owner` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`co_id`),
  KEY `fk_company_1_idx` (`co_owner`),
  CONSTRAINT `fk_company_1` FOREIGN KEY (`co_owner`) REFERENCES `user` (`us_username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (0,'sdfg',NULL,NULL);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logged_work`
--

DROP TABLE IF EXISTS `logged_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logged_work` (
  `lw_id` int(11) NOT NULL,
  `lw_hours_count` varchar(3) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lw_comment` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lw_log_time` datetime NOT NULL,
  `lw_username` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lw_task_id` int(11) NOT NULL,
  PRIMARY KEY (`lw_id`),
  KEY `fk_logged_work_1_idx` (`lw_username`),
  KEY `fk_logged_work_2_idx` (`lw_task_id`),
  CONSTRAINT `fk_logged_work_1` FOREIGN KEY (`lw_username`) REFERENCES `user` (`us_username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_logged_work_2` FOREIGN KEY (`lw_task_id`) REFERENCES `task` (`ta_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logged_work`
--

LOCK TABLES `logged_work` WRITE;
/*!40000 ALTER TABLE `logged_work` DISABLE KEYS */;
/*!40000 ALTER TABLE `logged_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `pe_id` int(11) NOT NULL,
  `pe_project_id` int(11) NOT NULL,
  `pe_type` enum('asign_to_other','comment','attachmanet','change_status') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`pe_id`),
  KEY `fk_permission_1_idx` (`pe_project_id`),
  CONSTRAINT `fk_permission_1` FOREIGN KEY (`pe_project_id`) REFERENCES `project` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `series` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
INSERT INTO `persistent_logins` VALUES ('sdfgbn00','rwdpUVKevx3yeORL35K1tg==','RNJNmOoMjiZUz2IDEY4wQg==','2017-06-11 15:40:57'),('sdfgbn00','xKgLeU0iQX5hBFs746TO7A==','3LF2KeRkeLFT7VciaUoqtA==','2017-06-11 17:17:00');
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `pr_id` int(11) NOT NULL AUTO_INCREMENT,
  `pr_name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pr_lead` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pr_description` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pr_logo` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pr_company_id` int(11) NOT NULL,
  PRIMARY KEY (`pr_id`),
  KEY `project_user_us_username_fk` (`pr_lead`),
  KEY `fk_project_1_idx` (`pr_company_id`),
  CONSTRAINT `fk_project_1` FOREIGN KEY (`pr_company_id`) REFERENCES `company` (`co_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_user_us_username_fk` FOREIGN KEY (`pr_lead`) REFERENCES `user` (`us_username`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'rtfygh','1','ghj','',0),(2,'vfvfd','1','ghj','',0),(3,'ngoino','1',NULL,NULL,0),(4,'ngoino','1',NULL,NULL,0),(11,'ewfr','sdfgbn00',NULL,NULL,0),(12,'ewfr123321','sdfgbn00',NULL,NULL,0),(13,'huiuh','1',NULL,NULL,0),(14,'huiuh','1',NULL,NULL,0),(15,'huiuh','1',NULL,NULL,0),(16,'huiuh','1',NULL,NULL,0),(17,'huiuh','1',NULL,NULL,0),(18,'huiuh','1',NULL,NULL,0),(19,'huiuh','1',NULL,NULL,0),(20,'huiuh','1',NULL,NULL,0),(21,'igigiu','sdfgbn00',NULL,NULL,0),(22,'wewef','sdfgbn00',NULL,NULL,0),(23,'jkbkj','sdfgbn00',NULL,NULL,0),(24,'jkbkj','sdfgbn00',NULL,NULL,0),(25,'Some Project','sdfgbn00',NULL,NULL,0),(26,'Some name','sdfgbn00',NULL,NULL,0);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_user`
--

DROP TABLE IF EXISTS `project_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_user` (
  `pu_project_id` int(11) NOT NULL,
  `pu_username` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`pu_project_id`,`pu_username`),
  KEY `fk_project_user_2` (`pu_username`),
  CONSTRAINT `fk_project_user_1` FOREIGN KEY (`pu_project_id`) REFERENCES `project` (`pr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_user_2` FOREIGN KEY (`pu_username`) REFERENCES `user` (`us_username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_user`
--

LOCK TABLES `project_user` WRITE;
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
INSERT INTO `project_user` VALUES (1,'1'),(1,'sdfgbn00'),(2,'1'),(2,'sdfgbn00'),(11,'sdfgbn00'),(12,'sdfgbn00'),(13,'1'),(14,'1'),(15,'1'),(16,'1'),(17,'1'),(18,'1'),(19,'1'),(20,'1'),(21,'sdfgbn00'),(22,'sdfgbn00'),(23,'sdfgbn00'),(24,'sdfgbn00'),(25,'sdfgbn00'),(26,'sdfgbn00');
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `ro_id` int(11) NOT NULL,
  `ro_name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ro_project_id` int(11) NOT NULL,
  `ro_max_task_number` int(3) NOT NULL,
  PRIMARY KEY (`ro_id`),
  KEY `fk_role_1_idx` (`ro_project_id`),
  CONSTRAINT `fk_role_1` FOREIGN KEY (`ro_project_id`) REFERENCES `project` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `rp_role_id` int(11) NOT NULL,
  `rp_permission_id` int(11) NOT NULL,
  PRIMARY KEY (`rp_role_id`,`rp_permission_id`),
  KEY `fk_role_permission_2_idx` (`rp_permission_id`),
  CONSTRAINT `fk_role_permission_1` FOREIGN KEY (`rp_role_id`) REFERENCES `role` (`ro_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_role_permission_2` FOREIGN KEY (`rp_permission_id`) REFERENCES `permission` (`pe_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_user`
--

DROP TABLE IF EXISTS `role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_user` (
  `ru_username` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ru_role_id` int(11) NOT NULL,
  PRIMARY KEY (`ru_username`,`ru_role_id`),
  KEY `fk_role_user_2_idx` (`ru_role_id`),
  CONSTRAINT `fk_role_user_1` FOREIGN KEY (`ru_username`) REFERENCES `user` (`us_username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_role_user_2` FOREIGN KEY (`ru_role_id`) REFERENCES `role` (`ro_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_user`
--

LOCK TABLES `role_user` WRITE;
/*!40000 ALTER TABLE `role_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `lb_id` int(11) NOT NULL,
  `lb_name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lb_color` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lb_project_id` int(11) NOT NULL,
  PRIMARY KEY (`lb_id`),
  KEY `fk_tag_1_idx` (`lb_project_id`),
  CONSTRAINT `fk_tag_1` FOREIGN KEY (`lb_project_id`) REFERENCES `project` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
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
  `tt_tag_id` int(11) NOT NULL,
  `tt_task_id` int(11) NOT NULL,
  PRIMARY KEY (`tt_tag_id`,`tt_task_id`),
  KEY `fk_tag_task_2_idx` (`tt_task_id`),
  CONSTRAINT `fk_tag_task_1` FOREIGN KEY (`tt_tag_id`) REFERENCES `tag` (`lb_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tag_task_2` FOREIGN KEY (`tt_task_id`) REFERENCES `task` (`ta_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
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
  `ta_id` int(11) NOT NULL AUTO_INCREMENT,
  `ta_name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ta_summary` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ta_description` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ta_priority` enum('NORMAL','LOW','HIGH','CRITICAL') COLLATE utf8mb4_unicode_ci NOT NULL,
  `ta_status` int(11) NOT NULL,
  `ta_creator` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ta_executor` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ta_create_time` datetime NOT NULL,
  `ta_order` int(3) DEFAULT NULL,
  `ta_project` int(11) NOT NULL,
  PRIMARY KEY (`ta_id`),
  KEY `fk_task_1_idx` (`ta_creator`),
  KEY `fk_task_2_idx` (`ta_executor`),
  KEY `fk_task_3_idx` (`ta_status`),
  KEY `fk_task_4_idx` (`ta_project`),
  CONSTRAINT `fk_task_1` FOREIGN KEY (`ta_creator`) REFERENCES `user` (`us_username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_2` FOREIGN KEY (`ta_executor`) REFERENCES `user` (`us_username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_3` FOREIGN KEY (`ta_status`) REFERENCES `task_status` (`ts_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_4` FOREIGN KEY (`ta_project`) REFERENCES `project` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'цруа','гшр','гшр','LOW',12,'sdfgbn00','sdfgbn00','0000-00-00 00:00:00',2,24),(2,'iojoij','oijoijoi',NULL,'HIGH',12,'sdfgbn00',NULL,'2017-06-10 18:55:49',2,24),(3,'iojoij','oijoijoi',NULL,'HIGH',12,'sdfgbn00',NULL,'2017-06-10 20:57:11',3,24),(4,'iuhiuh','iuhiu',NULL,'HIGH',12,'sdfgbn00',NULL,'2017-06-10 21:08:54',4,24),(5,'iuhiuh','iuhiu',NULL,'HIGH',15,'sdfgbn00',NULL,'2017-06-10 21:09:02',1,24),(8,'iuhiuh','iuhiu',NULL,'HIGH',15,'sdfgbn00',NULL,'2017-06-10 22:08:24',2,24),(9,'wefwef','wefew',NULL,'HIGH',17,'sdfgbn00',NULL,'2017-06-11 14:57:49',1,24),(10,'aaaaaa','uiyi',NULL,'HIGH',17,'sdfgbn00',NULL,'2017-06-11 14:58:11',2,24),(11,'wefwe','wfewef',NULL,'LOW',19,'sdfgbn00',NULL,'2017-06-11 15:09:10',1,24),(12,'wer','wer',NULL,'HIGH',12,'sdfgbn00',NULL,'2017-06-11 15:10:36',5,24),(13,'wer','wer',NULL,'HIGH',15,'sdfgbn00',NULL,'2017-06-11 15:10:45',3,24),(14,'Create','dfjwioefjoirfjoeifj',NULL,'HIGH',20,'sdfgbn00',NULL,'2017-06-11 16:36:38',3,25),(15,'ewiofjw','wefjoiwe',NULL,'CRITICAL',20,'sdfgbn00',NULL,'2017-06-11 17:40:56',2,25),(16,'iojo','oijoij',NULL,'NORMAL',21,'sdfgbn00',NULL,'2017-06-11 17:54:32',1,25),(17,'Write','wuegwiegiwueg',NULL,'LOW',21,'sdfgbn00',NULL,'2017-06-11 20:18:55',2,25);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_history`
--

DROP TABLE IF EXISTS `task_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_history` (
  `th_id` int(11) NOT NULL AUTO_INCREMENT,
  `th_time` datetime NOT NULL,
  `th_task_id` int(11) NOT NULL,
  `th_username` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `th_task_status` int(11) NOT NULL,
  PRIMARY KEY (`th_id`),
  KEY `fk_task_history_1_idx` (`th_task_id`),
  KEY `fk_task_history_2_idx` (`th_username`),
  KEY `fk_task_history_3_idx` (`th_task_status`),
  CONSTRAINT `fk_task_history_1` FOREIGN KEY (`th_task_id`) REFERENCES `task` (`ta_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_task_history_2` FOREIGN KEY (`th_username`) REFERENCES `user` (`us_username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_history_3` FOREIGN KEY (`th_task_status`) REFERENCES `task_status` (`ts_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_history`
--

LOCK TABLES `task_history` WRITE;
/*!40000 ALTER TABLE `task_history` DISABLE KEYS */;
INSERT INTO `task_history` VALUES (2,'2017-06-10 22:08:24',8,'sdfgbn00',15),(3,'2017-06-11 14:57:49',9,'sdfgbn00',17),(4,'2017-06-11 14:58:11',10,'sdfgbn00',17),(5,'2017-06-11 15:09:10',11,'sdfgbn00',19),(6,'2017-06-11 15:10:36',12,'sdfgbn00',12),(7,'2017-06-11 15:10:45',13,'sdfgbn00',15),(8,'2017-06-11 16:36:38',14,'sdfgbn00',20),(9,'2017-06-11 17:40:56',15,'sdfgbn00',20),(10,'2017-06-11 17:54:32',16,'sdfgbn00',20),(11,'2017-06-11 18:35:23',16,'sdfgbn00',20),(12,'2017-06-11 18:41:01',16,'sdfgbn00',21),(13,'2017-06-11 18:43:27',16,'sdfgbn00',20),(14,'2017-06-11 18:43:32',16,'sdfgbn00',21),(15,'2017-06-11 18:43:59',14,'sdfgbn00',21),(16,'2017-06-11 18:44:02',16,'sdfgbn00',20),(17,'2017-06-11 18:44:05',14,'sdfgbn00',20),(18,'2017-06-11 20:18:24',16,'sdfgbn00',21),(19,'2017-06-11 20:18:55',17,'sdfgbn00',21);
/*!40000 ALTER TABLE `task_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_status`
--

DROP TABLE IF EXISTS `task_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_status` (
  `ts_id` int(11) NOT NULL AUTO_INCREMENT,
  `ts_project` int(11) NOT NULL,
  `ts_name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ts_order` int(3) DEFAULT NULL,
  PRIMARY KEY (`ts_id`),
  KEY `fk_task_status_1_idx` (`ts_project`),
  CONSTRAINT `fk_task_status_1` FOREIGN KEY (`ts_project`) REFERENCES `project` (`pr_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_status`
--

LOCK TABLES `task_status` WRITE;
/*!40000 ALTER TABLE `task_status` DISABLE KEYS */;
INSERT INTO `task_status` VALUES (12,24,'SOme stsa',3),(15,24,'рпар',2),(16,24,'drtfyghj',3),(17,24,'khkh',4),(18,24,'wef',5),(19,24,'uihwre',6),(20,25,'New',1),(21,25,'In progress',2),(22,25,'Test',3);
/*!40000 ALTER TABLE `task_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `us_username` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `us_email` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `us_first_name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `us_last_name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `us_can_create_project` tinyint(4) NOT NULL,
  `us_company_id` int(11) NOT NULL DEFAULT '0',
  `us_password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `us_picture` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`us_username`),
  UNIQUE KEY `user_us_email_uindex` (`us_email`),
  KEY `fk_user_1_idx` (`us_company_id`),
  CONSTRAINT `fk_user_1` FOREIGN KEY (`us_company_id`) REFERENCES `company` (`co_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1','yuj','uhjk','tgyhjnk',0,0,'fghbjnk',NULL),('sdfgbn00','zvg96@mail.ru','qwfert','wqegrt',1,0,'$2a$06$Ybw/oPkPnBodWV3arIBDiuUbRoA9rOp3ztoSmEuP4591a7/VBoebW',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-11 23:22:43
