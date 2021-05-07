package me.travelplan.web.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.travelplan.web.common.FileDto;

import java.util.List;

public class PlaceResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetOne {
        private String name;
        private String address;
        private String openHour;
        private String category;
        private Double x;
        private Double y;
        private Integer likeCount;
        private boolean likeCheck;
        private Double score;
        private List<FileDto> images;
    }

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
