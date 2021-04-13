package me.travelplan.web.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PlaceResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Review {
        private PlaceDto.ReviewResponse review;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Reviews {
        private List<PlaceDto.ReviewResponse> reviews;
    }
}
