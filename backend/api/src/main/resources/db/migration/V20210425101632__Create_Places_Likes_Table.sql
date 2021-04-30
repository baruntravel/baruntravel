DROP TABLE IF EXISTS `places_likes`;
CREATE TABLE `places_likes`
(
    `id`         BIGINT   NOT NULL AUTO_INCREMENT,
    `place_id`   BIGINT   NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT   DEFAULT NULL,
    `updated_by` BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
