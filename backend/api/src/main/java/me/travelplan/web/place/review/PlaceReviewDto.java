package me.travelplan.web.place.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import me.travelplan.web.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class PlaceReviewDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private Double score;
        @NotBlank
        private String content;
        private List<MultipartFile> images;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private final Long id;
        private final Double score;
        private final String content;
        private final List<String> images;
        private final UserDto.Response createdBy;
        private final boolean isMine;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime updatedAt;
    }
}
