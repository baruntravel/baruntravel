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
        private Double centerX;
        private Double centerY;
        private Integer reviewCount;
        private Double score;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;
        private UserDto.Response creator;
        private List<RouteDto.RoutePlace> places;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetMine {
        List<RouteDto.RouteNameWithPlaceName> routes;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetList {
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
