CREATE DATABASE  IF NOT EXISTS `stockapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `stockapp`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: stockapp
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `IdProducto` int NOT NULL AUTO_INCREMENT,
  `NbrProducto` varchar(80) DEFAULT NULL,
  `PrecProducto` float NOT NULL,
  `StockProducto` int NOT NULL,
  `MarcProducto` varchar(25) DEFAULT NULL,
  `CatProducto` varchar(25) DEFAULT NULL,
  `CNetoProducto` varchar(25) DEFAULT NULL,
  `CodBarraProducto` bigint NOT NULL,
  PRIMARY KEY (`IdProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (19,'Arroz gallo oro',1575,25,'Gallo','Almacén','500gr',5478512232637),(20,'Arroz largo fino Lucchetti',2700,32,'Lucchetti','Almacén','500gr',7050502961547),(21,'Aceite de girasol Pureza',1975,15,'Pureza','Almacén','1.5l',8816978386431),(22,'Aceite de girasol Natura',2700,8,'Natura','Almacén','1.5l',3933383324121),(23,'Pure de tomates',664,16,'Alco','Almacén','520gr',1215981737911),(24,'Fideos tallarin Lucchetti',974,29,'Lucchetti','Almacén','520gr',2279758993796),(25,'Fideos tirabuzón',629,13,'Favorita','Almacén','500gr',6750850031851),(26,'Harina integral',1210,7,'Pureza','Almacén','1kg',7914973454469),(27,'Lavandina clásica',701,13,'Ayudín','Limpieza','1l',9322317453400),(28,'Lavandina en gel original',1155,2,'Ayudín','Limpieza','750ml',3866667180197),(29,'Limpiador líquido pisos jazmín',823,8,'Procenex','Limpieza','900ml',8366383817387),(30,'Lustramueble en aerosol',1670,4,'Ceramicol','Limpieza','360ml',2231917822949),(31,'Hamburguesa clásica',2745,5,'Swift','Congelados','4u',3060437939185),(32,'Hamburguesa clásica',1645,9,'Swift','Congelados','2u',7606436503683),(33,'Jugo de naranja Citric',1987,5,'Citric','Bebidas','1l',9850868977466),(34,'Gaseosa cola zero',2175,9,'Coca Cola','Bebidas','2.25l',7435035652885),(35,'Pasta dental triple acción',3275.75,6,'Colgate','Perfumería','180gr',6622571608630),(36,'Cepillo dental pro cuidado suave',1645,2,'Colgate','Perfumería','1u',9807751361100),(38,'Perfume Kevin Black',8940,2,'Kevin','Perfumería','100ml',98765654345);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-20 16:11:06
