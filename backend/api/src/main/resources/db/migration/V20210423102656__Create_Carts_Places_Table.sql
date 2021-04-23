DROP TABLE IF EXISTS `carts_places`;
CREATE TABLE `carts_places`
(
    `id`       BIGINT NOT NULL AUTO_INCREMENT,
    `cart_id`  BIGINT NOT NULL,
    `place_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
);
