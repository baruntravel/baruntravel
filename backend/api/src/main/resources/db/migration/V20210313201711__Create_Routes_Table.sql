DROP TABLE IF EXISTS `routes`;
CREATE TABLE `routes`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255) NOT NULL,
    `min_x`      DOUBLE       NOT NULL,
    `min_y`      DOUBLE       NOT NULL,
    `max_x`      DOUBLE       NOT NULL,
    `max_y`      DOUBLE       NOT NULL,
    `created_at` DATETIME     NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT   DEFAULT NULL,
    `updated_by` BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
