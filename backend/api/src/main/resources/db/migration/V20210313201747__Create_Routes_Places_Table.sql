USE `travelplan`;

DROP TABLE IF EXISTS `routes_places`;
CREATE TABLE `routes_places` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `route_id` BIGINT NOT NULL,
  `place_id` BIGINT NOT NULL,
  `order` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL,
  `created_by` BIGINT DEFAULT NULL,
  `updated_by` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`)
);
