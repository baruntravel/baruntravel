package me.travelplan.web.route.review.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class RouteReviewResponse {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetOnlyId {
        private Long id;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetList {
        private List<RouteReviewDto.Response> reviews;
    }

}
