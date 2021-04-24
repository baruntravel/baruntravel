DROP TABLE IF EXISTS `routes_reviews`;
CREATE TABLE `routes_reviews`
(
    `id`         BIGINT        NOT NULL AUTO_INCREMENT,
    `score`      DOUBLE,
    `content`    VARCHAR(1000) NOT NULL,
    `route_id`   BIGINT        NOT NULL,
    `created_at` DATETIME      NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT   DEFAULT NULL,
    `updated_by` BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
