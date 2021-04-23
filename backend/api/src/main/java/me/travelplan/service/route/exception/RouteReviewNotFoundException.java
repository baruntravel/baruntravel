package me.travelplan.service.route.exception;

import me.travelplan.exception.EntityNotFoundException;

public class RouteReviewNotFoundException extends EntityNotFoundException {
    public RouteReviewNotFoundException() {
        super("찾을 수 없는 경로리뷰입니다");
    }
}
