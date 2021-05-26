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
    public Page<RouteResponse.GetList> getList(PageDto pageDto,
                                               RouteRequest.GetList request,
                                               @CurrentUser CustomUserDetails customUserDetails) {
        List<Route> content = routeService.getList(request, pageDto).getContent();
        List<RouteResponse.GetList> getList = routeMapper.toGetListResponse(content, customUserDetails);

        return new PageImpl<>(getList, pageDto.of(), content.size());
    }

    @GetMapping("/my")
    public RouteResponse.GetMine getMine(@CurrentUser CustomUserDetails user) {
        return routeMapper.toGetMineResponse(routeService.getByUser(user.getUser()));
    }
}
