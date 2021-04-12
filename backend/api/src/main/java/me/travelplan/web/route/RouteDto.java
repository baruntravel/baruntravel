package me.travelplan.web.route;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import me.travelplan.web.common.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class RouteDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RoutePlace {
        private Long id;
        private String image;
        private String name;
        private String url;
        private Double x;
        private Double y;
        private String category;
        private Integer order;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RouteWithOnlyName {
        private Long id;
        private String name;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class ReviewRequest {
        Double score;
        String content;
        List<MultipartFile> files;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class ReviewResponse {
        private Long id;
        private String content;
        private Double score;
        private List<FileDto> files;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime updatedAt;
    }
}
