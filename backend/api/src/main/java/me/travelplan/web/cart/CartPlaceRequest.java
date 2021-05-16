package me.travelplan.web.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartPlaceRequest {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddPlace {
        private Long id;
        private String name;
        private String url;
        private Double x;
        private Double y;
        private String address;
        private String categoryId;
        private String categoryName;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddMemo {
        private String memo;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrder {
        private Long firstPlaceId;
        private Long secondPlaceId;
    }
}
