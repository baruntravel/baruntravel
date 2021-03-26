package me.travelplan.web.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.route.RouteMapper;
import me.travelplan.service.route.RouteService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/route")
public class RouteController {
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @PutMapping
    public void put(@RequestBody RouteRequest.Put request) {
        routeService.put(routeMapper.toEntity(request));
    }

    @GetMapping("/{id}")
    public RouteResponse.GetOne getOne(@PathVariable Long id) {
        return routeMapper.toResponse(routeService.getOne(id));
    }
}
