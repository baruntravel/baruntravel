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
    public static class Create {
        private String content;
        private Double score;
        private List<MultipartFile> files;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private String content;
        private Double score;
        private List<MultipartFile> files;
        private Boolean fileChange;
    }
}
