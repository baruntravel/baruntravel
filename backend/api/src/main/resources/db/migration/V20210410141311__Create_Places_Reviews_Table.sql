DROP TABLE IF EXISTS `places_reviews`;
CREATE TABLE `places_reviews` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `place_id` BIGINT NOT NULL,
 `score` DOUBLE NOT NULL,
 `content` VARCHAR(1000) NOT NULL,
 `created_at` DATETIME NOT NULL,
 `updated_at` DATETIME DEFAULT NULL,
 `deleted_at` DATETIME DEFAULT NULL,
 `created_by` BIGINT   DEFAULT NULL,
 `updated_by` BIGINT   DEFAULT NULL,
  PRIMARY KEY (`id`)
);
