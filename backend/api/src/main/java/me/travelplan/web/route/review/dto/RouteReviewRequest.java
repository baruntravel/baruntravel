package me.travelplan.web.route.review.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class RouteReviewRequest {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrUpdateReview {
        private String content;
        private Double score;
        List<MultipartFile> files;
    }
}
