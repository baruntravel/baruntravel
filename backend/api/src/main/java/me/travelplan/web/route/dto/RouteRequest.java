package me.travelplan.web.route.dto;

import lombok.*;

import java.util.List;

public class RouteRequest {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        private String name;
        private List<RouteDto.RoutePlaceWithIdAndOrder> places;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private Long firstPlaceId;
        private Long secondPlaceId;
    }

    @Getter
    @NoArgsConstructor
    public static class AddPlace {
        private RouteDto.RoutePlace place;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GetList {
        private Double maxX;
        private Double minX;
        private Double maxY;
        private Double minY;
    }
}
