DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
    `id` BIGINT(20) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `password` VARCHAR(32) DEFAULT NULL,
    `salt` VARCHAR(10) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `Product`;
CREATE TABLE `Product` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(16) DEFAULT NULL,
    `description` LONGTEXT,
    `price` DECIMAL(10, 2) DEFAULT '0.00',
    `count` INT(11) DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `OrderDetail`;
CREATE TABLE `OrderDetail` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) DEFAULT NULL,
    `product_id` BIGINT(20) DEFAULT NULL,
    `product_name` VARCHAR(16) DEFAULT NULL,
    `status` TINYINT(4) DEFAULT '0',
    `create_time` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `Order`;
CREATE TABLE `Order` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) DEFAULT NULL,
    `product_id` BIGINT(20) DEFAULT NULL,
    `order_id` BIGINT(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

INSERT INTO `User` VALUES(3105007261, "Langhao", "a1cf50cb0cd7ac425d2ecd0dc05f7dc3", "da4Kj32k");
INSERT INTO `Product` VALUES(1, "iPad Pro", "Apple's latest generation iPad Pro", 799.99, 1000);
INSERT INTO `Product` VALUES(2, "MacBook Pro", "Apple's latest generation Macbook", 2199.99, 1000);


