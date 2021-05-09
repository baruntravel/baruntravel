package me.travelplan.web.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.route.domain.Route;
import me.travelplan.service.route.RouteService;
import me.travelplan.web.common.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/routes")
public class RoutesController {
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @GetMapping
    public Page<RouteResponse.GetList> getList(PageRequest pageRequest,
                                               RouteRequest.GetList request,
                                               @CurrentUser CustomUserDetails customUserDetails) {
        List<Route> content = routeService.getList(request, pageRequest.of()).getContent();
        List<RouteResponse.GetList> getList = routeMapper.toGetListResponse(content, customUserDetails.getUser());

        return new PageImpl<>(getList, pageRequest.of(), content.size());
    }

    @GetMapping("my")
    public RouteResponse.GetsWithOnlyName getMine(@CurrentUser CustomUserDetails user) {
        return routeMapper.toGetsWithOnlyNameResponse(routeService.getByUser(user.getUser()));
    }
}
