package me.travelplan.service.route;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException() {
        super("찾을 수 없는 경로입니다");
    }
}
