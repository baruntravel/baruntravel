DROP TABLE IF EXISTS `places_reviews_likes`;
CREATE TABLE `places_reviews_likes`
(
    `id`              BIGINT   NOT NULL AUTO_INCREMENT,
    `place_review_id` BIGINT   NOT NULL,
    `created_at`      DATETIME NOT NULL,
    `updated_at`      DATETIME DEFAULT NULL,
    `deleted_at`      DATETIME DEFAULT NULL,
    `created_by`      BIGINT   DEFAULT NULL,
    `updated_by`      BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`)
);
