-- library_db.book definition

CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isborrowed` bit(1) NOT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- library_db.customer definition

CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- library_db.`transaction` definition

CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `borrow_date` datetime(6) DEFAULT NULL,
  `due_date` datetime(6) DEFAULT NULL,
  `late` bit(1) NOT NULL,
  `return_date` datetime(6) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnbpjofb5abhjg5hiovi0t3k57` (`customer_id`),
  CONSTRAINT `FKnbpjofb5abhjg5hiovi0t3k57` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- library_db.transaction_books definition

CREATE TABLE `transaction_books` (
  `transaction_id` bigint NOT NULL,
  `books_id` bigint NOT NULL,
  KEY `FK5t009xksf0bp62xk4kffxcn5s` (`books_id`),
  KEY `FKf228kdxoigu9j5o1wn3in7690` (`transaction_id`),
  CONSTRAINT `FK5t009xksf0bp62xk4kffxcn5s` FOREIGN KEY (`books_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKf228kdxoigu9j5o1wn3in7690` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;