-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: chatbot_db
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `question_id` bigint NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `user_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,1,'이렇게 하면 어떻게 되지?','2023-06-13 12:00:00','2023-06-13 12:00:00','a'),(2,2,'공부는 중요합니다.','2023-06-13 12:00:00','2023-06-13 12:00:00','a'),(3,21,'임시 테스트용 답변','2024-10-17 01:09:34','2024-10-17 01:09:34','user'),(4,22,'임시 테스트용 답변','2024-10-17 01:16:44','2024-10-17 01:16:44','user'),(5,23,'임시 테스트용 답변','2024-10-17 01:17:10','2024-10-17 01:17:10','user'),(6,24,'임시 테스트용 답변','2024-10-17 01:17:57','2024-10-17 01:17:57','user'),(7,25,'임시 테스트용 답변','2024-10-17 01:27:57','2024-10-17 01:27:57','user'),(8,26,'임시 테스트용 답변','2024-10-17 01:35:22','2024-10-17 01:35:22','user'),(9,27,'임시 테스트용 답변','2024-10-17 01:35:40','2024-10-17 01:35:40','user'),(10,28,'임시 테스트용 답변','2024-10-17 01:38:16','2024-10-17 01:38:16','user'),(11,29,'임시 테스트용 답변','2024-10-17 01:41:23','2024-10-17 01:41:23','user'),(12,30,'임시 테스트용 답변','2024-10-17 01:44:06','2024-10-17 01:44:06','user'),(13,32,'안녕하세요! 무엇을 도와드릴까요? \n','2024-10-17 01:45:58','2024-10-17 01:45:58','user'),(14,33,'네, 저는 잘 작동하고 있습니다! 무엇을 도와드릴까요? 궁금한 점이나 도움이 필요한 일이 있으면 알려주세요. 최선을 다해 도와드리겠습니다. \n','2024-10-17 01:46:08','2024-10-17 01:46:08','user'),(15,34,'Please provide me with some context or a question so I can assist you. \n\nFor example, you could ask me:\n\n* **To write a story.** \n* **To explain a concept.**\n* **To translate something.** \n* **To generate code.**\n* **To answer a question.**\n\nI\'m here to help! \n','2024-10-17 01:51:47','2024-10-17 01:51:47','user'),(16,35,'안녕하세요! 무엇을 도와드릴까요? \n','2024-10-17 01:51:55','2024-10-17 01:51:55','user');
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-17  1:56:47
