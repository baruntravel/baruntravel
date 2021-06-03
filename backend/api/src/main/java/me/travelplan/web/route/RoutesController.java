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
        List<Route> content = routeService.getListByRegion(request,pageDto).getContent();
        List<RouteResponse.GetListByRegion> getList = routeMapper.toListResponse(content);

        return new PageImpl<>(getList, pageDto.of(), content.size());
    }

    @GetMapping("/coordinate")
    public Page<RouteResponse.GetListByCoordinate> getListByCoordinate(PageDto pageDto, RouteRequest.GetListCoordinate request, @CurrentUser CustomUserDetails customUserDetails) {
        List<Route> content = routeService.getListByCoordinate(request, pageDto).getContent();
        List<RouteResponse.GetListByCoordinate> getList = routeMapper.toGetListResponse(content, customUserDetails);

        return new PageImpl<>(getList, pageDto.of(), content.size());
    }


    @GetMapping("/my")
    public RouteResponse.GetMine getMine(@CurrentUser CustomUserDetails user) {
        return routeMapper.toGetMineResponse(routeService.getByUser(user.getUser()));
    }
}
