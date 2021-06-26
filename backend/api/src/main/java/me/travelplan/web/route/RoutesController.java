package me.travelplan.web.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.route.RouteService;
import me.travelplan.service.route.domain.Route;
import me.travelplan.web.common.PageDto;
import me.travelplan.web.route.dto.RouteRequest;
import me.travelplan.web.route.dto.RouteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/routes")
public class RoutesController {
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @GetMapping
    public Page<RouteResponse.GetListByRegion> getListByRegion(PageDto pageDto, RouteRequest.GetListByRegion request) {
        Page<Route> routePage = routeService.getListByRegion(request, pageDto);
        List<RouteResponse.GetListByRegion> getList = routeMapper.toListResponse(routePage.getContent());

        return new PageImpl<>(getList, pageDto.of(), routePage.getTotalElements());
    }

    @GetMapping("/coordinate")
    public Page<RouteResponse.GetListByCoordinate> getListByCoordinate(PageDto pageDto, RouteRequest.GetListCoordinate request, @CurrentUser CustomUserDetails customUserDetails) {
        Page<Route> routePage = routeService.getListByCoordinate(request, pageDto);
        List<RouteResponse.GetListByCoordinate> getList = routeMapper.toListResponse(routePage.getContent(), customUserDetails);

        return new PageImpl<>(getList, pageDto.of(), routePage.getTotalElements());
    }

    @GetMapping("/my")
    public List<RouteResponse.GetMine> getMine(@CurrentUser CustomUserDetails user) {
        return routeMapper.toGetMineResponse(routeService.getByUser(user.getUser()));
    }
}
