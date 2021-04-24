DROP TABLE IF EXISTS `places`;
CREATE TABLE `places`
(
    `id`         BIGINT        NOT NULL,
    `category`   VARCHAR(100)  NOT NULL,
    `image`      BIGINT        NOT NULL,
    `name`       VARCHAR(255)  NOT NULL,
    `url`        VARCHAR(2083) NOT NULL,
    `x`          DOUBLE        NOT NULL,
    `y`          DOUBLE        NOT NULL,
    `created_at` DATETIME      NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT   DEFAULT NULL,
    `updated_by` BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
