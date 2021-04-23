package me.travelplan.web.cart;

import lombok.*;

import java.util.List;

public class CartResponse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetList {
        private List<CartDto.CartPlace> places;
    }
}
