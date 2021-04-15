package me.travelplan.service.place.exception;

import me.travelplan.exception.BusinessException;

public class PlaceReviewNotUpdatableException extends BusinessException {
    public PlaceReviewNotUpdatableException() {
        super("수정할 수 없는 장소 리뷰입니다");
    }
}
