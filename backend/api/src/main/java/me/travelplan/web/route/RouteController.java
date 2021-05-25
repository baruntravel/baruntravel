package me.travelplan.web.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.route.RouteService;
import me.travelplan.web.route.dto.RouteRequest;
import me.travelplan.web.route.dto.RouteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/route")
public class RouteController {
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponse.RouteId create(@RequestBody RouteRequest.Create request) {
        return routeMapper.toRouteIdResponse(routeService.create(request));
    }

    @PutMapping("/{id}")
    public void updatePlaceOrder(@PathVariable Long id, @RequestBody RouteRequest.Update request, @CurrentUser CustomUserDetails customUserDetails) {
        routeService.updatePlaceOrder(id, request, customUserDetails.getUser());
    }

    @GetMapping("/{id}")
    public RouteResponse.GetOne getOne(@PathVariable Long id) {
        return routeMapper.toGetOneResponse(routeService.getOne(id));
    }

    @PutMapping("/{id}/place")
    public void addPlace(@PathVariable Long id, @RequestBody RouteRequest.AddPlace request) {
        routeService.addPlace(id, routeMapper.toPlace(request));
    }

    @PostMapping("/{id}/like")
    public void like(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeService.createOrDeleteLike(id, userDetails.getUser());
    }
}
