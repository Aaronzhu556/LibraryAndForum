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
-- Table structure for table `t_borrow`
--

DROP TABLE IF EXISTS `t_borrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_borrow` (
  `borrow_id` int NOT NULL AUTO_INCREMENT,
  `borrow_book_id` varchar(45) DEFAULT NULL,
  `borrow_start_time` varchar(45) DEFAULT NULL,
  `borrow_end_time` varchar(45) DEFAULT NULL,
  `borrow_end_time_true` varchar(45) DEFAULT NULL,
  `borrow_user_name` varchar(45) DEFAULT NULL,
  `borrow_user_temperature` varchar(45) DEFAULT '36.5',
  `borrow_comment_context` varchar(500) DEFAULT NULL,
  `borrow_comment_time` varchar(45) DEFAULT NULL,
  `borrow_comment_star` int DEFAULT '0',
  PRIMARY KEY (`borrow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_borrow`
--

LOCK TABLES `t_borrow` WRITE;
/*!40000 ALTER TABLE `t_borrow` DISABLE KEYS */;
INSERT INTO `t_borrow` VALUES (9,'1','2022/04/01 12:28:51','2022/04/11 12:28:51','2022/04/18 16:45:04','user','36.2','得到','2022/04/18 19:39:19',1),(10,'2','2022/04/01 12:28:57','2022/04/11 12:28:57','2022/04/18 16:40:38','user','36.3','可以','2022/04/18 16:45:32',4),(11,'4','2022/04/01 12:29:02','2022/04/11 12:29:02','2022/04/18 12:35:19','user','36.5','还可以','2022/04/18 16:41:37',3),(13,'3','2022/04/10 18:14:01','2022/04/20 18:14:01','2022/04/15 13:06:11','user','36.2','很一般','2022/04/18 16:35:15',2),(14,'3','2022/04/16 16:50:39','2022/04/26 16:50:39','2022/04/16 16:57:21','user','36.2','这本书让我明白了许多关于中国的历史文化，我觉得地对地导弹得多多多多多多多多多多多多多多多多多多多多多','2022/04/18 16:31:37',5),(15,'2','2022/04/18 16:47:02','2022/04/28 16:47:02','2022/04/18 16:47:15','user','36.3','great','2022/04/18 16:48:03',4),(16,'3','2022/04/18 19:21:34','2022/04/28 19:21:34','2022/04/18 19:21:47','user','36.2','很不错哦','2022/04/18 19:22:14',5),(17,'1','2022/04/18 19:27:51','2022/04/28 19:27:51','2022/04/18 19:28:01','user','36.3','得到的','2022/04/18 19:39:33',1),(18,'1','2022/04/18 19:40:40','2022/04/28 19:40:40','2022/04/18 19:40:56','user','36.3','很不错的书籍','2022/04/18 19:52:06',5),(19,'2','2022/04/18 20:15:48','2022/04/28 20:15:48','2022/04/18 20:15:58','user','36.3','还可以嘛','2022/04/18 20:26:20',5),(20,'1','2022/04/24 20:20:30','2022/05/04 20:20:30','2022/04/25 23:15:38','user','36.3','很好','2022/04/25 23:15:46',5),(21,'15','2022/04/24 21:20:29','2022/05/04 21:20:29','2022/04/24 21:21:04','user','36.2','很棒的书籍','2022/04/24 21:21:16',5),(22,'1','2022/04/26 22:40:27','2022/05/06 22:40:27','2022/04/26 22:42:38','user1','36.2','hainicoi ','2022/04/26 22:42:49',5),(23,'13','2022/05/10 18:52:13','2022/05/20 18:52:13','2022/05/10 18:52:26','user','36.2','感觉还行','2022/05/10 18:52:38',4),(24,'13','2022/05/10 19:03:54','2022/05/20 19:03:54','2022/05/10 19:04:25','zhu','36.3','这本书是我读过最好的经济史数据，推荐！','2022/05/10 19:10:24',5);
/*!40000 ALTER TABLE `t_borrow` ENABLE KEYS */;
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
