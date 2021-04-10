package me.travelplan.web.place;

import lombok.*;

public class PlaceDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Review {
        Double score;
        String content;
    }
}
