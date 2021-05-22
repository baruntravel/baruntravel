DROP TABLE IF EXISTS `places_reviews_images`;
CREATE TABLE `places_reviews_images`
(
    `id`              BIGINT NOT NULL AUTO_INCREMENT,
    `review_id`       BIGINT NOT NULL,
    `file_id`         BIGINT NOT NULL,
    PRIMARY KEY (`id`)
);
