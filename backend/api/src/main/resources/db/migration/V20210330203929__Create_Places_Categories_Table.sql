DROP TABLE IF EXISTS `places_categories`;
CREATE TABLE `places_categories` (
  `id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `places_categories` (id, name) VALUES
('MT1', '대형마트'),
('CS2', '편의점'),
('PS3', '어린이집, 유치원'),
('SC4', '학교'),
('AC5', '학원'),
('PK6', '주차장'),
('OL7', '주유소, 충전소'),
('SW8', '지하철역'),
('BK9', '은행'),
('CT1', '문화시설'),
('AG2', '중개업소'),
('PO3', '공공기관'),
('AT4', '관광명소'),
('AD5', '숙박'),
('FD6', '음식점'),
('CE7', '카페'),
('HP8', '병원'),
('PM9', '약국');
