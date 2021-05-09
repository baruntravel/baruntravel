DROP TABLE IF EXISTS `places_images`;
CREATE TABLE `places_images`
(
    `id`         BIGINT   NOT NULL AUTO_INCREMENT,
    `place_id`   BIGINT   NOT NULL,
    `file_id`    BIGINT   NOT NULL,
    PRIMARY KEY (`id`)
);
