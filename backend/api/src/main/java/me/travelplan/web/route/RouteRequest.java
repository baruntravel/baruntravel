package me.travelplan.web.route;

import lombok.Getter;

import java.util.List;

public class RouteRequest {

    @Getter
    public static class Put {
        private String name;
        List<RouteDto.RoutePlace> places;
    }
}
