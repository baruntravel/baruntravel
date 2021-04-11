package me.travelplan.web.place;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PlaceResponse {
    public static class ReviewOne {
        public PlaceDto.ReviewResponse review;
    }
}
