package me.travelplan.web.wishlist.dto;

import lombok.*;

public class WishlistDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Place {
        private Long id;
        private String name;
        private String image;
    }
}
