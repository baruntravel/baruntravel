DROP TABLE IF EXISTS `routes_likes`;
CREATE TABLE `routes_likes`
(
    `id`         BIGINT   NOT NULL AUTO_INCREMENT,
    `route_id`   BIGINT   NOT NULL,
    `like_check` BOOLEAN  NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT   DEFAULT NULL,
    `updated_by` BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
