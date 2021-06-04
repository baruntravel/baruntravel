package me.travelplan.web.route.review;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.route.RouteReviewService;
import me.travelplan.service.route.domain.RouteReview;
import me.travelplan.web.common.PageDto;
import me.travelplan.web.route.review.dto.RouteReviewRequest;
import me.travelplan.web.route.review.dto.RouteReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/route")
public class RouteReviewController {
    private final RouteReviewService routeReviewService;
    private final RouteReviewMapper routeReviewMapper;

    @PostMapping("/{id}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public RouteReviewResponse.GetOnlyId create(@PathVariable Long id, RouteReviewRequest.Create request) {
        return routeReviewMapper.toReviewIdResponse(routeReviewService.create(request, id));
    }

    @GetMapping("/{id}/reviews")
    public Page<RouteReviewResponse.GetList> getList(@PathVariable Long id, PageDto pageDto, @CurrentUser CustomUserDetails customUserDetails) {
        List<RouteReview> content = routeReviewService.getList(id, pageDto).getContent();
        List<RouteReviewResponse.GetList> getList = routeReviewMapper.entityToResponseReviewList(content, customUserDetails);

        return new PageImpl<>(getList, pageDto.of(), content.size());
    }

    @PostMapping("/review/{id}")
    public void update(@PathVariable Long id, RouteReviewRequest.Update request, @CurrentUser CustomUserDetails userDetails) {
        routeReviewService.update(id, request, userDetails.getUser());
    }

    @PostMapping("/review/{id}/like")
    public void like(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeReviewService.createOrDeleteLike(id, userDetails.getUser());
    }

    @DeleteMapping("/review/{id}")
    public void delete(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        routeReviewService.delete(id, userDetails.getUser());
    }
}
