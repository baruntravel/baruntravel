package me.travelplan.service.cart.exception;

import me.travelplan.exception.BusinessException;

public class PlaceDuplicatedException extends BusinessException {
    public PlaceDuplicatedException() {
        super("카트에 이미 존재하는 장소입니다");
    }
}
