package me.travelplan.service.route;

import me.travelplan.exception.EntityNotFoundException;

public class RouteNotFoundException extends EntityNotFoundException {

    public RouteNotFoundException(String message) {
        super(message);
    }
}
