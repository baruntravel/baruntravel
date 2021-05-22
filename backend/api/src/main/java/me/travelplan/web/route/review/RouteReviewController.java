package me.travelplan.web.route.review;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.route.RouteReviewService;
import me.travelplan.web.route.review.dto.RouteReviewRequest;
import me.travelplan.web.route.review.dto.RouteReviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/route")
public class RouteReviewController {
    private final RouteReviewService routeReviewService;
    private final RouteReviewMapper routeReviewMapper;

    @PostMapping("/{id}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public RouteReviewResponse.GetOnlyId createReview(@PathVariable Long id, RouteReviewRequest.CreateOrUpdateReview request) {
        return routeReviewMapper.toReviewIdResponse(routeReviewService.create(request, id));
    }

    @GetMapping("/{id}/reviews")
    public RouteReviewResponse.GetList getReviewList(@PathVariable Long id, @CurrentUser CustomUserDetails customUserDetails) {
        return routeReviewMapper.entityToResponseReviewList(routeReviewService.getList(id), customUserDetails);
    }

    @PostMapping("/review/{id}")
    public void updateReview(@PathVariable Long id, RouteReviewRequest.CreateOrUpdateReview request, @CurrentUser CustomUserDetails userDetails) {
        routeReviewService.update(id, request, userDetails.getUser());
    }

    @PostMapping("/review/{id}/like")
    public void createOrUpdateReviewLike(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeReviewService.createOrDeleteLike(id, userDetails.getUser());
    }

    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeReviewService.delete(id, userDetails.getUser());
    }
}
