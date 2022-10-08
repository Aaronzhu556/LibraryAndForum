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
-- Table structure for table `t_lost`
--

DROP TABLE IF EXISTS `t_lost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_lost` (
  `lost_id` int NOT NULL AUTO_INCREMENT,
  `lost_name` varchar(45) DEFAULT NULL,
  `lost_details` varchar(450) DEFAULT NULL,
  `lost_time` varchar(45) DEFAULT NULL,
  `lost_img` varchar(500) DEFAULT NULL,
  `lost_status` varchar(10) DEFAULT '0',
  `lost_delete_status` int DEFAULT '1',
  PRIMARY KEY (`lost_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_lost`
--

LOCK TABLES `t_lost` WRITE;
/*!40000 ALTER TABLE `t_lost` DISABLE KEYS */;
INSERT INTO `t_lost` VALUES (16,'钥匙','有一个粉色的兔子挂件','2022/05/08 00:00:00','','1',0),(17,'鼠标垫','黑色的鼠标垫，比较新','2022/05/07 00:00:00','','1',0),(18,'钥匙','在周四捡到的','2022/03/31 00:00:00','/api/path/1653484216467.png','0',1),(19,'耳机','airpods','2022/05/20 00:00:00','/api/path/1653484259375.jpg','0',1),(20,'书籍','五楼捡到的','2022/05/06 00:00:00','/api/path/1653484291537.jpg','0',1),(21,'书籍','三楼晚上拾到的','2022/05/05 00:00:00','/api/path/1653484328179.jpg','0',1),(22,'钥匙','一个钥匙','2022/05/06 00:00:00','/api/path/1653484346513.png','0',1),(23,'手表','黑色手表','2022/05/06 00:00:00','/api/path/1653484467142.jpg','0',1),(24,'手表','金色边框手表','2022/05/06 00:00:00','/api/path/1653484500059.jpg','0',1),(25,'手机','黑色','2022/05/18 00:00:00','/api/path/1653485322923.jpg','0',1),(26,'电脑','黑色电脑','2022/05/20 00:00:00','/api/path/1653485347510.jpg','0',1);
/*!40000 ALTER TABLE `t_lost` ENABLE KEYS */;
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
