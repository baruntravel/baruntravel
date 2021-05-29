package me.travelplan.web.route.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import me.travelplan.web.common.FileDto;
import me.travelplan.web.common.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public class RouteReviewDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private Long id;
        private String content;
        private Double score;
        private Integer likes;
        private Boolean isLike;
        private UserDto.Response creator;
        private List<FileDto.Image> images;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;
    }
}
