package me.travelplan.service.route;

import me.travelplan.service.exception.ResponsibleServerException;

public class RouteNotFoundException extends ResponsibleServerException {
    public RouteNotFoundException() {
        super("찾을 수 없는 경로입니다");
    }
}
