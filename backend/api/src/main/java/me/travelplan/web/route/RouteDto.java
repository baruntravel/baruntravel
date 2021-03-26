package me.travelplan.web.route;

import lombok.Getter;

public class RouteDto {
    @Getter
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
