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
-- Table structure for table `t_book`
--

DROP TABLE IF EXISTS `t_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_book` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(45) DEFAULT NULL,
  `book_img` varchar(45) DEFAULT NULL,
  `book_isbn` varchar(45) DEFAULT NULL,
  `book_status` varchar(45) DEFAULT '1',
  `book_money` varchar(45) DEFAULT NULL,
  `book_category` int DEFAULT NULL,
  `book_address` varchar(45) DEFAULT NULL,
  `book_context` varchar(500) DEFAULT NULL,
  `book_author` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE KEY `book_id_UNIQUE` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_book`
--

LOCK TABLES `t_book` WRITE;
/*!40000 ALTER TABLE `t_book` DISABLE KEYS */;
INSERT INTO `t_book` VALUES (13,'中国近代经济史','/api/path/1650023806814.jpg','65469454','1','300',2,'人民出版社','这是一本关于经济学的',NULL),(15,'简爱','/api/path/1650290139789.jpg','56485961','1','300',2,'人民出版社','这是一本好书',NULL),(16,'算法导论','/api/path/1650806738781.jpg','5684655','1','300',2,'麻省理工学院出版社','算法导论是学习算法最好的书籍',NULL),(17,'百年孤独','/api/path/1652179468769.jpg','854645561','1','89',2,'人民出版社','《百年孤独》，是哥伦比亚作家加西亚·马尔克斯创作的长篇小说，是其代表作，也是拉丁美洲魔幻现实主义文学的代表作，被誉为“再现拉丁美洲历史社会图景的鸿篇巨著”。\n作品描写了布恩迪亚家族七代人的传奇故事，以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史。作品融入神话传说、民间故事、宗教典故等神秘因素，巧妙地糅合了现实与虚幻，展现出一个瑰丽的想象世界，成为20世纪重要的经典文学巨著之一。','加西亚·马尔克斯'),(18,'霍乱时期的爱情','/api/path/1652179550002.jpg','564561654','1','60',2,'上海大学出版社','《霍乱时期的爱情》是哥伦比亚作家加西亚·马尔克斯创作的长篇小说，首次出版于1985年。\n该小说讲述了一段跨越半个多世纪的爱情故事：男女主人公在二十岁的时候没能结婚，因为他们太年轻了；经过各种人生曲折之后，到了八十岁，他们还是没能结婚，因为他们太老了。在五十年的时间跨度中，作者展示了所有爱情的可能性，所有的爱情方式。\n该小说不仅表达了“经历爱情的折磨是一种尊严”，更重要的是展现了哥伦比亚的历史。战争和霍乱威胁着拉美人民的生命，而人为的破坏加剧了人与自然的对立，人的社会孤独感使人与人之间缺乏理解信任，心理距离加大。\n','加西亚·马尔克斯'),(19,'计量经济学','/api/path/1652179629403.jpg','65456265641','1','150',4,'中国人民大学出版社','《计量经济学》是2012年格致出版社出版的图书，作者是[美] 詹姆斯·斯托克，马克·W.沃森。','詹姆斯·斯托克'),(20,'区域经济学','/api/path/1652179722588.jpg','654495245984','1','80',4,'清华大学出版社','《区域经济学》是2008年清华大学出版社出版的图书，作者是栾贵勤等。\n本书系统地阐述了区域经济领域在我国十七大以后积极谋划发展大计所急需的理论指引和实践指导，客观的揭示了我国跨世纪发展中的区域经济问题，有针对性地提出了先进地区的成功经验，探索适合我国各省市区域经济发展的途径和调整方略。\n','栾贵勤'),(21,'小王子','/api/path/1652179804798.jpg','567864894','1','78',2,'人民出版社','《小王子》是法国作家安托万·德·圣-埃克苏佩里于1942年写成的著名儿童文学短篇小说。本书的主人公是来自外星球的小王子。书中以一位飞行员作为故事叙述者，讲述了小王子从自己星球出发前往地球的过程中，所经历的各种历险。作者以小王子的孩子式的眼光，透视出成人的空虚、盲目，愚妄和死板教条，用浅显天真的语言写出了人类的孤独寂寞、没有根基随风流浪的命运。同时，也表达出作者对金钱关系的批判，对真善美的讴歌。','安托万·德·圣-埃克苏佩里'),(22,'一千零一夜','/api/path/1652179858628.jpg','29552251598','1','160',2,'人民出版社','该作讲述古代阿拉伯地区有一位国王叫山鲁亚尔，他生性残暴嫉妒，因王后行为不端，将其杀死，此后每日娶一少女，翌日晨即杀掉，以示报复。宫相维齐尔的女儿山鲁佐德为拯救无辜的女子，自愿嫁给国王。山鲁佐德用讲述故事方法吸引国王，每夜讲到最精彩处，天刚好亮了，使国王因爱听故事而不忍杀她，允许她下一夜继续讲。她的故事一直讲了一千零一夜，国王终于被感动，与她白首偕老。因其内容丰富，规模宏大，故被高尔基誉为世界民间文学史上“最壮丽的一座纪念碑”。','民间作家');
/*!40000 ALTER TABLE `t_book` ENABLE KEYS */;
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
