package me.travelplan.web.route;

import lombok.Getter;

public class RouteDto {
    @Getter
    public static class RoutePlace {
        private String image;
        private Long thirdId;
        private String name;
        private String url;
        private Double x;
        private Double y;
        private Integer order;
    }
}
