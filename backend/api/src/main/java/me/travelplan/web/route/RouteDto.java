package me.travelplan.web.route;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import me.travelplan.web.common.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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
    public static class RoutePlaceWithIdAndName {
        private Long id;
        private String name;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RouteNameWithPlaceName {
        private Long id;
        private String name;
        private List<RouteDto.RoutePlaceWithIdAndName> places;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Creator {
        private String name;
        private String avatar;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RoutePlaceWithIdAndOrder {
        private Long id;
        private Integer order;
    }


    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class ReviewRequest {
        Double score;
        String content;
        List<MultipartFile> files;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class ReviewResponse {
        private Long id;
        private String content;
        private Double score;
        private RouteDto.Creator creator;
        private Integer likeCount;
        private boolean likeCheck;
        private List<FileDto> files;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime updatedAt;

    }
}
