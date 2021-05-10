package me.travelplan.web.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.route.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
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
    public void updatePlaceOrder(@PathVariable Long id, @RequestBody RouteRequest.Update request) {
        routeService.updatePlaceOrder(id, request);
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
    public void createOrUpdateLike(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeService.createOrDeleteLike(id, userDetails.getUser());
    }

    @PostMapping("/{id}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@PathVariable Long id, RouteRequest.CreateOrUpdateReview request) {
        routeService.createReview(request, id);
    }

    @GetMapping("/{id}/reviews")
    public RouteResponse.ReviewList getReviewList(@PathVariable Long id, @CurrentUser CustomUserDetails customUserDetails) {
        return routeMapper.entityToResponseReviewList(routeService.getReviewList(id), customUserDetails.getUser());
    }

    @PostMapping("/review/{id}")
    public void updateReview(@PathVariable Long id, RouteRequest.CreateOrUpdateReview request, @CurrentUser CustomUserDetails userDetails) {
        routeService.updateReview(id, request, userDetails.getUser());
    }

    @PostMapping("/review/{id}/like")
    public void createOrUpdateReviewLike(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeService.createOrDeleteReviewLike(id, userDetails.getUser());
    }

    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeService.deleteReview(id, userDetails.getUser());
    }
}
