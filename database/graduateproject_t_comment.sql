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
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `comment_user_name` varchar(45) DEFAULT NULL,
  `comment_time` varchar(45) DEFAULT NULL,
  `comment_content` varchar(45) DEFAULT NULL,
  `comment_article_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_comment`
--

LOCK TABLES `t_comment` WRITE;
/*!40000 ALTER TABLE `t_comment` DISABLE KEYS */;
INSERT INTO `t_comment` VALUES (1,'user','2022/04/01 11:27:42','测试','1'),(2,'user','2022/04/01 11:28:41','再次测试','1'),(3,'user','2022/04/01 11:29:47','我没有看过，你有什么见解吗','2'),(4,'user','2022/04/01 11:36:09','我不想看了  好垃圾','2'),(6,'user','2022/04/01 17:10:51','你好  这是一条新的回复','10'),(7,'user','2022/04/01 17:11:19','测试+1','1'),(8,'user','2022/04/01 17:14:57','这是一条新回复','11'),(9,'user','2022/04/01 17:15:11','这是第二条回复','11'),(10,'user','2022/04/01 17:15:23','测试++++++1','1'),(11,'user','2022/04/09 00:34:54','dd','1'),(12,'user','2022/04/10 15:50:09','测试评论数据','1'),(13,'user','2022/04/10 18:21:08','没有看过。。。','3'),(14,'user','2022/04/10 18:21:40','回复测试','12'),(20,'zhu','2022/04/21 23:19:39','++1','23'),(21,'zhu','2022/04/21 23:19:45','奇怪','23'),(22,'zhu','2022/04/21 23:19:54','Interesting','23'),(23,'zhu','2022/04/21 23:22:25','哈哈哈','23'),(24,'zhu','2022/04/21 23:22:32','你好','27'),(25,'zhu','2022/04/21 23:22:43','懂的都懂','28'),(26,'zhu','2022/04/21 23:33:29','打滴滴','24'),(27,'zhu','2022/04/21 23:33:38','地对地导弹得多','23'),(28,'zhu','2022/04/21 23:33:41','阿达的','23'),(29,'zhu','2022/04/21 23:33:44','是的','23'),(30,'zhu','2022/04/21 23:33:47','啊啊啊','23'),(31,'zhu','2022/04/21 23:34:11','ad','29'),(32,'zhu','2022/04/21 23:34:12','的','29'),(33,'zhu','2022/04/21 23:34:14','得到','29'),(34,'zhu','2022/04/21 23:34:16','啊啊啊','29'),(35,'zhu','2022/04/21 23:34:18','得到的','29'),(36,'zhu','2022/04/21 23:34:59','地对地导弹得多','29'),(37,'zhu','2022/04/21 23:35:56','得到的','29'),(38,'zhu','2022/04/21 23:35:58','得到','29'),(39,'user','2022/04/23 17:26:57','别紧张 多发几次大家就熟悉了','23'),(40,'user','2022/04/24 21:46:40','你好','23'),(42,'user','2022/04/27 10:34:10','interesting','23'),(43,'user','2022/04/27 10:34:19','e ','23'),(44,'user','2022/05/15 16:58:57','什么时候毕业呢？','34'),(45,'user','2022/05/15 17:34:17','有没有什么好的话题呀？','33'),(46,'user','2022/05/15 18:01:10','我有很多，住在3栋欢迎交换','60'),(47,'user','2022/05/15 18:01:39','这世界偷偷的爱着你','59'),(48,'user','2022/05/15 18:02:12','注意字体大小，按要求','64'),(49,'user','2022/05/15 18:02:36','最好不要','53'),(50,'user','2022/05/15 18:03:11','这个比较适合女孩子哟','40'),(51,'user','2022/05/15 18:03:31','三体Yyds','36'),(52,'user','2022/05/15 18:04:54','求一个三体爱好者交流心得','36'),(53,'user','2022/05/15 18:05:03','快来一个三体爱好者','36'),(54,'user','2022/05/15 18:05:11','三体3','36'),(55,'user','2022/05/15 18:05:52','测试','39'),(56,'user','2022/05/15 18:05:55','测试','39'),(57,'user','2022/05/15 18:05:58','测试','39'),(58,'user','2022/05/15 18:06:01','测试','39'),(59,'user','2022/05/15 18:06:36','沟通真的是一门艺术，很难','70'),(60,'user','2022/05/15 18:07:20','我记得我看的是视频课','43'),(61,'zhu','2022/05/16 10:06:03','我也很喜欢哦','36'),(62,'zhu','2022/05/16 10:07:36','可以去b站看视频哟','39'),(63,'user1','2022/05/16 10:36:04','基本上都是需要改动的哦，要记录用户的行为','85'),(64,'user1','2022/05/16 10:36:51','咖啡豆真的挺难的','99'),(65,'user','2022/05/16 15:35:19','回复','25');
/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;
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
