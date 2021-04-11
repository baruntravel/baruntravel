package me.travelplan.service.place.exception;

import me.travelplan.exception.EntityNotFoundException;

public class PlaceNotFoundException extends EntityNotFoundException {
    public PlaceNotFoundException() {
        super("찾을 수 없는 장소입니다");
    }
}
