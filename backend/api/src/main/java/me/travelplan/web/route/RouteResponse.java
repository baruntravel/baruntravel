package me.travelplan.web.route;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class RouteResponse {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RouteId {
        private Long id;
        private String name;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetOne {
        private String name;
        private Double minX;
        private Double maxX;
        private Double minY;
        private Double maxY;
        private List<RouteDto.RoutePlace> places;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetsWithOnlyName {
        List<RouteDto.RouteWithOnlyName> routes;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetList {
        private String name;
        private List<RouteDto.RoutePlace> places;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ReviewOne {
        private RouteDto.ReviewResponse review;
    }

}
