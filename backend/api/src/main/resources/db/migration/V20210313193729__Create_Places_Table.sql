DROP TABLE IF EXISTS `places`;
CREATE TABLE `places`
(
    `id`            BIGINT        NOT NULL,
    `name`          VARCHAR(255)  NOT NULL,
    `url`           VARCHAR(2083) NOT NULL,
    `x`             DOUBLE        NOT NULL,
    `y`             DOUBLE        NOT NULL,
    `address`       VARCHAR(255)  NOT NULL,
    `detail_status` VARCHAR(255)  NOT NULL,
    `category_id`   VARCHAR(100)  NOT NULL,
    `open_hour`     VARCHAR(100)  NOT NUlL DEFAULT '',
    `file_id`       BIGINT        NULL,
    `created_at`    DATETIME      NOT NULL,
    `updated_at`    DATETIME DEFAULT NULL,
    `deleted_at`    DATETIME DEFAULT NULL,
    `created_by`    BIGINT   DEFAULT NULL,
    `updated_by`    BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
