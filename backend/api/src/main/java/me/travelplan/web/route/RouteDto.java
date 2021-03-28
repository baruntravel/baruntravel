package me.travelplan.web.route;

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
        private String url;
        private Double x;
        private Double y;
        private Integer order;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RouteWithOnlyName {
        private Long id;
        private String name;
    }
}
