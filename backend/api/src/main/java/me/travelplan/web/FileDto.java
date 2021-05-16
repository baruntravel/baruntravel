package me.travelplan.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FileDto {
    @Getter
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Image {
        private final String url;
    }
}
