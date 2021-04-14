package me.travelplan.service.place.exception;

import me.travelplan.exception.BusinessException;

public class PlaceNotUpdatableException extends BusinessException {
    public PlaceNotUpdatableException() {
        super("수정할 수 없는 장소입니다");
    }
}
