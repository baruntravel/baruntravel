package me.travelplan.service.cart.exception;

import me.travelplan.exception.BusinessException;

public class PlaceExistedException extends BusinessException {
    public PlaceExistedException() {
        super("카트에 이미 존재하는 장소입니다");
    }
}
