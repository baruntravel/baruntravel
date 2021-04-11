package me.travelplan.web.place;

import lombok.*;

public class PlaceRequest {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class PutReview {
        PlaceDto.ReviewRequest review;
    }
}
