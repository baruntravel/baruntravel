USE `travelplan`;

DROP TABLE IF EXISTS `routes_places`;
CREATE TABLE `routes_places` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `route_id` BIGINT NOT NULL,
  `place_id` BIGINT NOT NULL,
  `order` INT NOT NULL,
  PRIMARY KEY (`id`)
);
