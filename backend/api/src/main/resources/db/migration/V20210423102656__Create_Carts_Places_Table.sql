DROP TABLE IF EXISTS `carts_places`;
CREATE TABLE `carts_places`
(
    `id`         BIGINT   NOT NULL AUTO_INCREMENT,
    `order`      INT      NOT NULL,
    `memo`       VARCHAR(1000),
    `place_id`   BIGINT   NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT   DEFAULT NULL,
    `updated_by` BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
