DROP TABLE IF EXISTS `routes`;
CREATE TABLE `routes` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `min_x` DOUBLE,
  `max_x` DOUBLE,
  `min_y` DOUBLE,
  `max_y` DOUBLE,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL,
  `created_by` BIGINT DEFAULT NULL,
  `updated_by` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`)
);
