package me.travelplan.service.route;

import me.travelplan.exception.EntityNotFoundException;

public class RouteReviewNotFoundException extends EntityNotFoundException {
    public RouteReviewNotFoundException(String message) {
        super(message);
    }
}
