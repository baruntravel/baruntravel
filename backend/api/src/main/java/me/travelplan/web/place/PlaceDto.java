package me.travelplan.web.place;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class PlaceDto {
    public static class Place {
        private Long id;
        private String name;
        private String url;
        private Double x;
        private Double y;
        private String address;
        private String categoryId;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class ReviewRequest {
        Double score;
        String content;
        List<MultipartFile> images;
    }

    public static class ReviewResponse {
        public Long id;
        public String content;
        public Double score;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime updatedAt;
    }
}