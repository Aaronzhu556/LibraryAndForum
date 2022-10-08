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
-- Table structure for table `t_like`
--

DROP TABLE IF EXISTS `t_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_like` (
  `like_id` int NOT NULL AUTO_INCREMENT,
  `like_article_id` int DEFAULT NULL,
  `like_user_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`like_id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_like`
--

LOCK TABLES `t_like` WRITE;
/*!40000 ALTER TABLE `t_like` DISABLE KEYS */;
INSERT INTO `t_like` VALUES (19,2,'user'),(25,1,'user'),(28,17,'user'),(30,22,'zhu'),(33,25,'zhu'),(34,23,'zhu'),(35,29,'zhu'),(42,22,'ccc'),(43,23,'ccc'),(44,24,'ccc'),(49,34,'user'),(50,70,'user'),(51,62,'user'),(52,61,'user'),(53,63,'user'),(54,60,'user'),(55,58,'user'),(56,59,'user'),(57,69,'user'),(58,68,'user'),(59,67,'user'),(60,66,'user'),(61,65,'user'),(62,64,'user'),(63,54,'user'),(64,53,'user'),(65,52,'user'),(66,51,'user'),(67,46,'user'),(68,45,'user'),(69,44,'user'),(70,43,'user'),(71,42,'user'),(72,41,'user'),(73,40,'user'),(74,26,'zhu'),(75,27,'zhu'),(76,28,'zhu'),(77,55,'zhu'),(78,54,'zhu'),(79,52,'zhu'),(80,51,'zhu'),(81,42,'zhu'),(82,41,'zhu'),(83,43,'zhu'),(84,44,'zhu'),(85,46,'zhu'),(86,47,'zhu'),(87,71,'user1'),(88,87,'user1'),(89,86,'user1'),(90,85,'user1'),(91,79,'user1'),(92,78,'user1'),(93,76,'user1'),(94,94,'user1'),(95,101,'user1'),(96,102,'user1'),(97,103,'user1'),(98,99,'user1'),(99,98,'user1'),(100,96,'user1'),(102,22,'user'),(103,23,'user'),(104,24,'user'),(105,25,'user'),(106,39,'user');
/*!40000 ALTER TABLE `t_like` ENABLE KEYS */;
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
