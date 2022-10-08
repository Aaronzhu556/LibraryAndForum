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
-- Table structure for table `t_reply`
--

DROP TABLE IF EXISTS `t_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_reply` (
  `reply_id` int NOT NULL AUTO_INCREMENT,
  `reply_from` varchar(45) DEFAULT NULL,
  `reply_to` varchar(45) DEFAULT NULL,
  `reply_content` varchar(45) DEFAULT NULL,
  `reply_time` varchar(45) DEFAULT NULL,
  `reply_comment_id` int DEFAULT NULL,
  `reply_status` varchar(10) DEFAULT '0',
  PRIMARY KEY (`reply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_reply`
--

LOCK TABLES `t_reply` WRITE;
/*!40000 ALTER TABLE `t_reply` DISABLE KEYS */;
INSERT INTO `t_reply` VALUES (7,'user','zhu','怎么了','2022/04/23 17:08:59',21,'1'),(8,'user','zhu','哈哈哈哈','2022/04/23 17:17:39',23,'1'),(9,'user','zhu','灌灌灌灌','2022/04/23 17:17:54',28,'1'),(10,'user','zhu','哈哈哈哈','2022/04/23 21:03:34',22,'1'),(11,'user','zhu','怎么啦','2022/04/23 21:03:53',30,'1'),(13,'user','zhu','你好！','2022/04/24 21:45:23',16,'1'),(14,'zhu','user','你好！','2022/04/24 21:45:39',16,'1'),(15,'user','zhu','OKOK','2022/04/26 23:11:14',18,'1'),(16,'user','user','三生三世','2022/04/26 23:11:35',18,'1'),(18,'user','user','好像是2022.6.1','2022/05/15 16:59:43',44,'1'),(19,'zhu','user','是的呀','2022/05/16 10:04:56',51,'1'),(20,'user','user','回复','2022/05/16 15:35:30',65,'1'),(21,'user','zhu','回复','2022/05/16 16:03:09',26,'0'),(23,'user','zhu','你好','2022/05/26 22:49:01',17,'0');
/*!40000 ALTER TABLE `t_reply` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-08 19:10:05
