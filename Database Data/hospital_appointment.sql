-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hospital
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `doctor_username` varchar(50) NOT NULL,
  `patient_username` varchar(50) DEFAULT NULL,
  `appointment_date` date NOT NULL,
  PRIMARY KEY (`doctor_username`,`appointment_date`),
  KEY `patient_username` (`patient_username`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`doctor_username`) REFERENCES `doctor` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('drkhan',NULL,'2025-09-19'),('drkhan',NULL,'2026-11-19'),('drlee',NULL,'2029-03-19'),('drsmith',NULL,'2025-07-18'),('drsmith',NULL,'2026-06-20'),('drsmith',NULL,'2026-10-10'),('drsmith',NULL,'2026-12-29'),('drchan','patelena','2025-06-19'),('drlee','patelena','2025-06-16'),('drsmith','patelena','2025-06-22'),('drchan','patjohn','2025-06-20'),('drlee','patjohn','2025-06-23'),('drsmith','patjohn','2025-06-15'),('drkhan','patmaria','2025-06-18'),('drkhan','patmaria','2025-07-13'),('drkhan','patmaria','2025-07-25'),('drkhan','patmaria','2025-08-10'),('drkhan','patmaria','2029-09-23'),('drsmith','patmaria','2025-02-19'),('drsmith','patmaria','2025-06-14'),('drsmith','patmaria','2025-06-20'),('drsmith','patmaria','2025-07-07'),('drsmith','patmaria','2025-08-18'),('drkhan','patnikos','2025-06-21'),('drlee','patnikos','2025-06-17');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-06 17:16:02
