package me.travelplan.web.route.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class RouteReviewRequest {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrUpdateReview {
        private String content;
        private Double score;
        List<MultipartFile> files;
    }
}
