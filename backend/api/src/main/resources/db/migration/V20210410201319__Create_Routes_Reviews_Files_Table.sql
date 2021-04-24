DROP TABLE IF EXISTS `routes_reviews_files`;
CREATE TABLE `routes_reviews_files`
(
    `id`              BIGINT NOT NULL AUTO_INCREMENT,
    `file_id`         BIGINT NOT NULL,
    `route_review_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
);
