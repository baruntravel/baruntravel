package me.travelplan.web.route;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class RouteDto {

    @Getter
    @Builder
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
}
