package me.travelplan.web.route.dto;

import lombok.*;

public class RouteDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RoutePlace {
        private Long id;
        private String image;
        private String name;
        private String address;
        private String url;
        private Double x;
        private Double y;
        private String category;
        private Integer order;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PlaceWithIdAndNameAndOrder {
        private Long id;
        private String name;
        private Integer order;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RoutePlaceWithIdAndOrder {
        private Long id;
        private Integer order;
    }
}
