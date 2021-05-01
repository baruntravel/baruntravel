package me.travelplan.web.route;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
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
        private Double centerX;
        private Double centerY;
        private String createdBy;
        private Integer reviewCount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime updatedAt;
        private List<RouteDto.RoutePlace> places;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetsWithOnlyName {
        List<RouteDto.RouteWithOnlyName> routes;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetList {
        private Long id;
        private String name;
        private Integer likeCount;
        private boolean likeCheck;
        private List<RouteDto.RoutePlace> places;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ReviewList {
        private List<RouteDto.ReviewResponse> reviews;
    }
}
