package me.travelplan.web.route;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


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
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReview {
        private String content;
        private Double score;
        List<MultipartFile> files;
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
