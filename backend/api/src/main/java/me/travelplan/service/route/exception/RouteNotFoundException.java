package me.travelplan.service.route.exception;

import me.travelplan.exception.EntityNotFoundException;

public class RouteNotFoundException extends EntityNotFoundException {

    public RouteNotFoundException(String message) {
        super(message);
    }
}
