package me.travelplan.web.route;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


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
    @AllArgsConstructor
    public static class CreateOrUpdateReview {
        private String content;
        private Double score;
        List<MultipartFile> files;
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
