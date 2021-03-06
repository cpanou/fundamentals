-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

CREATE DATABASE `eshop` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

CREATE TABLE `eshop`.`users` (
  `firstname` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `lastname` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `eshop`.`orders` (
  `orderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `submitDate` timestamp NULL DEFAULT NULL,
  `processDate` timestamp NULL DEFAULT NULL,
  `status` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`orderId`),
  KEY `USER_ID_FK_idx` (`userId`),
  CONSTRAINT `USER_ID_FK` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `eshop`.`products` (
  `productName` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `productId` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `eshop`.`orderproducts` (
  `orderId` bigint(20) NOT NULL,
  `productId` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `ORDER_ID_FK_idx` (`orderId`),
  KEY `PRODUCT_ID_FK_idx` (`productId`),
  CONSTRAINT `ORDER_ID_FK` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `PRODUCT_ID_FK` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

