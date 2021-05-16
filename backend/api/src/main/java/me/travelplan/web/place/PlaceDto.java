package me.travelplan.web.place;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.travelplan.web.FileDto;

import java.util.List;

public class PlaceDto {
    public static class Request {
        // TODO
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private final Long id;
        private final String name;
        private final String url;
        private final Double x;
        private final Double y;
        private final String address;
        private final Double score;
        private final String categoryId;
        private final String categoryName;
        private final String thumbnailUrl;
        private final List<FileDto.Image> images;
        private final Integer likes;
        private final Boolean isLike;
        private final String openHour;
    }
}