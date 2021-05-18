package me.travelplan.service.place.exception;

import me.travelplan.exception.BusinessException;
import me.travelplan.exception.PermissionDeniedException;

public class PlaceReviewNotUpdatableException extends PermissionDeniedException {
    public PlaceReviewNotUpdatableException() {
        super("수정할 수 없는 장소 리뷰입니다");
    }
}
