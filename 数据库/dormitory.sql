CREATE DATABASE  IF NOT EXISTS `dormitory_system` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `dormitory_system`;
-- MySQL dump 10.13  Distrib 5.7.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dormitory_system
-- ------------------------------------------------------
-- Server version	5.7.26

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
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `rooms_max` int(11) NOT NULL,
  `note` varchar(45) COLLATE utf8_unicode_ci DEFAULT '无',
  `gender` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES (1,'A1',20,'东北角','女'),(2,'A2',25,'东北角第二栋','女'),(3,'B1',25,'  ','男'),(4,'B2',30,'  ','男'),(5,'C1',20,'  ','女'),(6,'C2',25,'环境好','男'),(7,'D1',20,'新楼','女'),(8,'D2',25,'新楼','女');
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building_manager`
--

DROP TABLE IF EXISTS `building_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buildingid` int(11) NOT NULL,
  `managerid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `building_idx` (`buildingid`),
  KEY `manager_idx` (`managerid`),
  CONSTRAINT `building` FOREIGN KEY (`buildingid`) REFERENCES `building` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `manager` FOREIGN KEY (`managerid`) REFERENCES `manager` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building_manager`
--

LOCK TABLES `building_manager` WRITE;
/*!40000 ALTER TABLE `building_manager` DISABLE KEYS */;
INSERT INTO `building_manager` VALUES (3,3,3),(4,4,4),(7,1,3),(9,6,2),(11,5,8),(12,2,4);
/*!40000 ALTER TABLE `building_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dormitory`
--

DROP TABLE IF EXISTS `dormitory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dormitory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `contain` int(11) NOT NULL,
  `note` varchar(45) COLLATE utf8_unicode_ci DEFAULT '无',
  `buildingid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dor_building_idx` (`buildingid`),
  CONSTRAINT `dor_building` FOREIGN KEY (`buildingid`) REFERENCES `building` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dormitory`
--

LOCK TABLES `dormitory` WRITE;
/*!40000 ALTER TABLE `dormitory` DISABLE KEYS */;
INSERT INTO `dormitory` VALUES (1,'A1101',8,' ',1),(2,'A2101',4,' ',2),(3,'B1101',6,' ',3),(4,'B2101',6,' ',4),(5,'C1101',8,' ',5),(6,'C2101',8,' ',6),(11,'A1201',6,'上床下桌',1),(12,'D2101',6,'dsfa',8),(13,'A1102',8,'上床下桌',1),(14,'A1103',8,'独立卫浴',1),(15,'A2102',6,'研究生宿舍',2),(16,'A2103',6,'去年整修',2),(17,'B1102',8,'研究生宿舍',3),(18,'B1201',8,'独立卫浴',3),(19,'B2102',8,'豪华宿舍',4),(20,'B2103',6,'豪华宿舍',4),(21,'C1102',6,'上床下桌',5),(22,'C1201',8,'上床下桌',5),(23,'C2102',6,'',6),(24,'C2103',6,'绿化好',6);
/*!40000 ALTER TABLE `dormitory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `gender` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(245) COLLATE utf8_unicode_ci DEFAULT 'null.jpg',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,'王树花','女','null.jpg'),(2,'刘国强','男','null.jpg'),(3,'藤振伯','男','null.jpg'),(4,'马美丽','女','null.jpg'),(8,'孙新华','男','null.jpg'),(9,'唐家宗','男','null.jpg'),(10,'张梅琳','女','f0e5358923b94171bca447b6b4afd93d.png'),(12,'王传盛','男','f9d6fd63b29b4babb0da33ba51c73b98.png');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stu_dor`
--

DROP TABLE IF EXISTS `stu_dor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stu_dor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuid` int(11) NOT NULL,
  `dorid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stu_dor_idx` (`stuid`),
  KEY `dor_idx` (`dorid`,`stuid`),
  CONSTRAINT `dor` FOREIGN KEY (`dorid`) REFERENCES `dormitory` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `stu` FOREIGN KEY (`stuid`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_dor`
--

LOCK TABLES `stu_dor` WRITE;
/*!40000 ALTER TABLE `stu_dor` DISABLE KEYS */;
INSERT INTO `stu_dor` VALUES (2,2,1),(19,12,1),(12,15,2),(14,1,3),(15,3,4),(20,10,4),(16,7,5),(18,9,5),(22,17,12),(27,18,13),(28,19,16),(23,8,17),(24,11,18),(25,13,19),(26,16,20),(29,20,23),(30,21,24);
/*!40000 ALTER TABLE `stu_dor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `age` int(11) DEFAULT NULL,
  `major` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `class` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gender` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'张三',21,'软件工程','20一班','13066010965','男'),(2,'丽华',18,'智能科学','21二班','13066845642','女'),(3,'王五',19,'计算机科学','20一班','15656776545','男'),(7,'李华',21,'软件工程','20一班','13088976876','女'),(8,'胜寒',22,'计算机科学','21二班','13877897465','男'),(9,'张萌',20,'智能科学','21一班','13488767850','女'),(10,'可古',22,'网络工程','20一班','13288076566','男'),(11,'张迪',21,'信息安全','20二班','13088766509','男'),(12,'王蕊',20,'软件工程','20一班','13566787889','女'),(13,'王生务',21,'软件工程','20二班','13677888609','男'),(15,'张二花',21,'计算机科学','21二班','13098908789','女'),(16,'萨达姆',22,'智能科学','20二班','13566745654','男'),(17,'刘思彤',19,'网络工程','21一班','15677678908','女'),(18,'王安娜',21,'计算机科学','21一班','15455656766','女'),(19,'刘思琪',19,'软件工程','20二班','15677879450','女'),(20,'王勇欧',22,'网络工程','20一班','15644590897','男'),(21,'刘坤',21,'智能科学','20一班','15644310970','男'),(22,'藤刚',22,'计算机科学','20一班','15699097669','男'),(23,'王志杨',21,'智能科学','20一班','','男'),(24,'孙洋',20,'软件工程','21一班','13077890980','男'),(25,'杨娜',20,'计算机科学','20一班','13099087649','女'),(26,'华梅',19,'网络工程','20一班','13088090888','女'),(27,'娜琳双',20,'网络工程','20一班','13088966789','女');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `nickname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','202CB962AC59075B964B07152D234B70','admin',0),(2,'syz','202CB962AC59075B964B07152D234B70','孙养振',0),(3,'shf','202CB962AC59075B964B07152D234B70','shf',0),(14,'ms','202CB962AC59075B964B07152D234B70','马帅',0),(15,'tzb','202CB962AC59075B964B07152D234B70','tzb',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `view_building_allocation`
--

DROP TABLE IF EXISTS `view_building_allocation`;
/*!50001 DROP VIEW IF EXISTS `view_building_allocation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_building_allocation` AS SELECT 
 1 AS `id`,
 1 AS `tag`,
 1 AS `rooms_max`,
 1 AS `note`,
 1 AS `gender`,
 1 AS `has_allocation`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `view_building_manager`
--

DROP TABLE IF EXISTS `view_building_manager`;
/*!50001 DROP VIEW IF EXISTS `view_building_manager`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_building_manager` AS SELECT 
 1 AS `id`,
 1 AS `tag`,
 1 AS `rooms_max`,
 1 AS `note`,
 1 AS `gender`,
 1 AS `manager_name`,
 1 AS `manager_gender`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `view_dor_building`
--

DROP TABLE IF EXISTS `view_dor_building`;
/*!50001 DROP VIEW IF EXISTS `view_dor_building`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_dor_building` AS SELECT 
 1 AS `id`,
 1 AS `tag`,
 1 AS `contain`,
 1 AS `note`,
 1 AS `buildingid`,
 1 AS `building_tag`,
 1 AS `building_gender`,
 1 AS `building_note`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `view_stu_dor_building`
--

DROP TABLE IF EXISTS `view_stu_dor_building`;
/*!50001 DROP VIEW IF EXISTS `view_stu_dor_building`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_stu_dor_building` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `age`,
 1 AS `major`,
 1 AS `class`,
 1 AS `phone`,
 1 AS `gender`,
 1 AS `dor_id`,
 1 AS `dor_tag`,
 1 AS `contain`,
 1 AS `note`,
 1 AS `building_id`,
 1 AS `building_tag`,
 1 AS `building_gender`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `view_building_allocation`
--

/*!50001 DROP VIEW IF EXISTS `view_building_allocation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_building_allocation` AS select `a`.`id` AS `id`,`a`.`tag` AS `tag`,`a`.`rooms_max` AS `rooms_max`,`a`.`note` AS `note`,`a`.`gender` AS `gender`,(select count(0) from `dormitory` where (`a`.`id` = `dormitory`.`buildingid`)) AS `has_allocation` from `building` `a` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_building_manager`
--

/*!50001 DROP VIEW IF EXISTS `view_building_manager`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_building_manager` AS select `a`.`id` AS `id`,`a`.`tag` AS `tag`,`a`.`rooms_max` AS `rooms_max`,`a`.`note` AS `note`,`a`.`gender` AS `gender`,`c`.`name` AS `manager_name`,`c`.`gender` AS `manager_gender` from ((`building` `a` left join `building_manager` `b` on((`a`.`id` = `b`.`buildingid`))) left join `manager` `c` on((`b`.`managerid` = `c`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_dor_building`
--

/*!50001 DROP VIEW IF EXISTS `view_dor_building`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_dor_building` AS select `a`.`id` AS `id`,`a`.`tag` AS `tag`,`a`.`contain` AS `contain`,`a`.`note` AS `note`,`a`.`buildingid` AS `buildingid`,`b`.`tag` AS `building_tag`,`b`.`gender` AS `building_gender`,`b`.`note` AS `building_note` from (`dormitory` `a` left join `building` `b` on((`a`.`buildingid` = `b`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_stu_dor_building`
--

/*!50001 DROP VIEW IF EXISTS `view_stu_dor_building`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_stu_dor_building` AS select `a`.`id` AS `id`,`a`.`name` AS `name`,`a`.`age` AS `age`,`a`.`major` AS `major`,`a`.`class` AS `class`,`a`.`phone` AS `phone`,`a`.`gender` AS `gender`,`c`.`id` AS `dor_id`,`c`.`tag` AS `dor_tag`,`c`.`contain` AS `contain`,`c`.`note` AS `note`,`d`.`id` AS `building_id`,`d`.`tag` AS `building_tag`,`d`.`gender` AS `building_gender` from (((`student` `a` left join `stu_dor` `b` on((`a`.`id` = `b`.`stuid`))) left join `dormitory` `c` on((`b`.`dorid` = `c`.`id`))) left join `building` `d` on((`c`.`buildingid` = `d`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-17 19:03:32
