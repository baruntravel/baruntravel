package me.travelplan.web.route.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import me.travelplan.web.common.UserDto;

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
        private Double score;
        private Double centerX;
        private Double centerY;
        private Integer reviewCount;
        private UserDto.Response creator;
        private List<RouteDto.RoutePlace> places;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetMine {
        private Long id;
        private String name;
        private String image;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetListByRegion {
        private Long id;
        private String name;
        private String region;
        private UserDto.Response creator;
        private List<RouteDto.PlaceWithIdAndNameAndOrder> places;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetListByCoordinate {
        private Long id;
        private String name;
        private Double centerX;
        private Double centerY;
        private String createdBy;
        private Integer likes;
        private Boolean isLike;
        private List<RouteDto.RoutePlace> places;
    }
}
