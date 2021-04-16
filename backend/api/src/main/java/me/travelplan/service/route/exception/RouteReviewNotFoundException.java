package me.travelplan.service.route.exception;

import me.travelplan.exception.EntityNotFoundException;

public class RouteReviewNotFoundException extends EntityNotFoundException {
    public RouteReviewNotFoundException(String message) {
        super(message);
    }
}
