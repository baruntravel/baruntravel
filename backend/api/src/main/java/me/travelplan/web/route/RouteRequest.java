package me.travelplan.web.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RouteRequest {
    @Getter
    @NoArgsConstructor
    public static class CreateEmpty {
        private String name;
    }

    @Getter
    @NoArgsConstructor
    public static class CreateOrUpdate {
        private String name;
        private List<RouteDto.RoutePlace> places;
    }

    @Getter
    @NoArgsConstructor
    public static class AddPlace {
        private RouteDto.RoutePlace place;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReview {
        private String content;
        private Double score;
    }

    @Getter
    @NoArgsConstructor
    public static class GetList {
        private Double maxX;
        private Double minX;
        private Double maxY;
        private Double minY;
    }


}
