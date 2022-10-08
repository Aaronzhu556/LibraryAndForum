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
-- Table structure for table `t_notice`
--

DROP TABLE IF EXISTS `t_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(45) DEFAULT NULL,
  `notice_content` varchar(450) DEFAULT NULL,
  `notice_time` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_notice`
--

LOCK TABLES `t_notice` WRITE;
/*!40000 ALTER TABLE `t_notice` DISABLE KEYS */;
INSERT INTO `t_notice` VALUES (2,'图书馆公告','小王子即将到货','2022/05/03 14:58:04'),(3,'系统公告','欢迎各位会员！','2022/05/03 14:58:40'),(7,'图书馆维修公告','下周三三楼左侧灯需要维修，请提前借书哟','2022/05/15 22:58:48'),(8,'疫情防控指南','进入图书馆需持绿码进入，并记录体温','2022/05/16 09:51:31'),(9,'发帖公告','请大家注意发帖用语，遵守发帖准则','2022/05/16 09:52:43'),(10,'桌椅维修','周四三楼桌椅维修关闭','2022/05/25 21:02:28'),(11,'图书到货','有一批最新的图书到货','2022/05/25 21:03:19'),(12,'失物招领','掉东西捡到东西的到管理员中心联系登记','2022/05/25 21:03:52'),(13,'图书借阅','每次最多借阅书籍有限哟','2022/05/25 21:04:25'),(14,'失物招领','拾到兔子挂件','2022/05/25 21:05:23'),(15,'文明读书','不要在图书馆大声喧哗','2022/05/25 21:05:45'),(16,'疫情最新信息','由于疫情防控，闭馆两天','2022/05/25 21:06:17'),(17,'预约座位','请合理预约','2022/05/25 21:06:41'),(18,'爱护图书','请大家爱护书籍','2022/05/25 21:06:57'),(19,'爱护桌椅','请大家爱护桌椅','2022/05/25 21:07:12'),(20,'轻声细语','不要在午休的时候大声喧哗','2022/05/25 21:07:31'),(21,'失物招领','周四掉落一串钥匙','2022/05/25 21:07:54');
/*!40000 ALTER TABLE `t_notice` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-08 19:10:03
