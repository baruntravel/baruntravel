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
        private String categoryId;
        private String categoryName;
        private String address;
        private Double x;
        private Double y;
        private String url;
        private Integer order;
        private String memo;
        private boolean likeCheck;
    }
}
