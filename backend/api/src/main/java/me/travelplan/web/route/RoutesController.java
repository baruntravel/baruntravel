package me.travelplan.web.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.route.RouteMapper;
import me.travelplan.service.route.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/routes")
public class RoutesController {
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @GetMapping("my")
    public RouteResponse.GetsWithOnlyName getMine(@CurrentUser CustomUserDetails user) {
        return routeMapper.toGetsWithOnlyNameResponse(routeService.getByUser(user.getUser()));
    }
}
