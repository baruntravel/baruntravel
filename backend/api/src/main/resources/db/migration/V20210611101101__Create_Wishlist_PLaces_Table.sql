DROP TABLE IF EXISTS `wishlist_places`;
CREATE TABLE `wishlist_places`
(
    `id`          BIGINT   NOT NULL AUTO_INCREMENT,
    `wishlist_id` BIGINT   NOT NULL,
    `place_id`    BIGINT   NOT NULL,
    `created_at`  DATETIME NOT NULL,
    `updated_at`  DATETIME DEFAULT NULL,
    `deleted_at`  DATETIME DEFAULT NULL,
    `created_by`  BIGINT   DEFAULT NULL,
    `updated_by`  BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
