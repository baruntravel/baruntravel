package me.travelplan.web.cart;

import lombok.*;

import java.util.List;

public class CartPlaceResponse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetList {
        private List<CartPlaceDto.Place> places;
    }
}
