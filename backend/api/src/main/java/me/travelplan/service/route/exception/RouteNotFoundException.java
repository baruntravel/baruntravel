package me.travelplan.service.route.exception;

import me.travelplan.exception.EntityNotFoundException;

public class RouteNotFoundException extends EntityNotFoundException {
    public RouteNotFoundException() {
        super("찾을 수 없는 경로입니다");
    }
}
