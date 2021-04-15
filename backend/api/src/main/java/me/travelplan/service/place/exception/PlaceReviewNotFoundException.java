package me.travelplan.service.place.exception;

import me.travelplan.exception.EntityNotFoundException;

public class PlaceReviewNotFoundException extends EntityNotFoundException {
    public PlaceReviewNotFoundException() {
        super("찾을 수 없는 장소 리뷰입니다");
    }
}
