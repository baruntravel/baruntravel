package me.travelplan.web.route;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RouteRequest {

    @Getter
    @NoArgsConstructor
    public static class Put {
        private String name;
        private List<RouteDto.RoutePlace> places;
    }
}
