package me.travelplan.web.cart;

import lombok.*;

public class CartPlaceDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Place {
        private Long id;
        private String name;
        private String category;
        private String url;
        private String image;
        private boolean likeCheck;
    }
}
