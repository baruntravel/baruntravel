package me.travelplan.service.cart.exception;

import me.travelplan.exception.EntityNotFoundException;

public class CartPlaceNotFoundException extends EntityNotFoundException {
    public CartPlaceNotFoundException() {
        super("카트 담긴 장소에 해당 장소가 없습니다");
    }
}
