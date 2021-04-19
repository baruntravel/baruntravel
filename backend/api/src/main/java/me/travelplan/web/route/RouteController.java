package me.travelplan.web.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.route.RouteMapper;
import me.travelplan.service.route.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/route")
public class RouteController {
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @PostMapping("/empty")
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponse.RouteId createEmpty(@RequestBody RouteRequest.CreateEmpty request) {
        return routeMapper.toRouteIdResponse(routeService.createEmpty(routeMapper.toEntity(request)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponse.RouteId create(@RequestBody RouteRequest.CreateOrUpdate request) {
        return routeMapper.toRouteIdResponse(routeService.create(routeMapper.toEntity(request, 0L)));
    }


    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody RouteRequest.CreateOrUpdate request) {
        routeService.update(routeMapper.toEntity(request, id));
    }

    @GetMapping("/{id}")
    public RouteResponse.GetOne getOne(@PathVariable Long id) {
        return routeMapper.toGetOneResponse(routeService.getOne(id));
    }

    @PutMapping("/{id}/place")
    public void addPlace(@PathVariable Long id, @RequestBody RouteRequest.AddPlace request) {
        routeService.addPlace(id, routeMapper.toPlace(request));
    }

    @PostMapping("/{id}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@PathVariable Long id, RouteRequest.CreateOrUpdateReview request) {
        routeService.createReview(request, id);
    }

    @GetMapping("/{id}/reviews")
    public RouteResponse.ReviewList getReviewList(@PathVariable Long id) {
        return routeMapper.entityToResponseReviewList(routeService.getReviewList(id));
    }

    //test에서 file을 처리할 때 fileUpload가 post형식임
    //PutMapping을 사용할 방법은??
    @PostMapping("/review/{id}")
    public void updateReview(@PathVariable Long id, RouteRequest.CreateOrUpdateReview request, @CurrentUser CustomUserDetails userDetails) {
        routeService.updateReview(id, request, userDetails.getUser());
    }

    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeService.deleteReview(id, userDetails.getUser());
    }

    @PostMapping("/{id}/like")
    public void createOrUpdateLike(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeService.createOrUpdateLike(id, userDetails.getUser());
    }
}
