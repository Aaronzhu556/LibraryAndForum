-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: graduateproject
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_find`
--

DROP TABLE IF EXISTS `t_find`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_find` (
  `find_id` int NOT NULL AUTO_INCREMENT,
  `find_name` varchar(45) DEFAULT NULL,
  `find_details` varchar(450) DEFAULT NULL,
  `find_time` varchar(45) DEFAULT NULL,
  `find_img` varchar(45) DEFAULT NULL,
  `find_status` varchar(10) DEFAULT '0',
  `find_level` varchar(10) DEFAULT NULL,
  `find_delete_status` int DEFAULT '1',
  PRIMARY KEY (`find_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_find`
--

LOCK TABLES `t_find` WRITE;
/*!40000 ALTER TABLE `t_find` DISABLE KEYS */;
INSERT INTO `t_find` VALUES (6,'Airpods','前天在三楼自习室掉了','2022/05/08 16:00:00','/api/path/1652181718210.jpg','0','紧急',1),(7,'书包','黑色的Nike挎包','2022/05/10 00:00:00','','0','紧急',0),(8,'测试','测试','2022/05/05 00:00:00','/api/path/1653492738248.jpg','0','紧急',0),(9,'手机','黑色','2022/05/02 00:00:00','/api/path/1653492798450.jpg','1','紧急',1),(10,'手机','黑色','2022/05/05 00:00:00','/api/path/1653576863662.jpg','0','紧急',1);
/*!40000 ALTER TABLE `t_find` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-08 19:10:04
