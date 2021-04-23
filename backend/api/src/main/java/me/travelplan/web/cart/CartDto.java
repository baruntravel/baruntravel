package me.travelplan.web.cart;

import lombok.*;

public class CartDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CartPlace {
        private Long id;
        private String name;
        private String category;
        private String url;
        private String image;
    }
}
