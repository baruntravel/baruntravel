package me.travelplan.web.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.place.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/place")
@RestController
public class PlaceController {
    private final PlaceMapper placeMapper;
    private final PlaceService placeService;

    @GetMapping("/{id}")
    public PlaceResponse.GetOne getOne(@PathVariable Long id,@CurrentUser CustomUserDetails customUserDetails) {
        return placeMapper.entityToGetOneDto(placeService.getOne(id),customUserDetails.getUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{placeId}/review")
    public PlaceResponse.Reviews getReviews(@PathVariable Long placeId) {
        return PlaceResponse.Reviews.builder().reviews(placeMapper.entityToDto(placeService.getReviews(placeId))).build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{placeId}/review")
    public void createReview(@PathVariable Long placeId, @RequestBody PlaceRequest.PutReview request) {
        placeService.createReview(placeId, placeMapper.requestToEntity(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{placeId}/review/{reviewId}")
    public PlaceResponse.Review updateReview(@PathVariable Long reviewId, @RequestBody PlaceRequest.PutReview request, @CurrentUser CustomUserDetails userDetails) {
        placeService.checkReviewUpdatable(reviewId, userDetails.getUser());
        return PlaceResponse.Review.builder()
                .review(placeMapper.entityToDto(placeService.updateReview(placeMapper.requestToEntity(reviewId, request))))
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{placeId}/review/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, @CurrentUser CustomUserDetails userDetails) {
        placeService.checkReviewUpdatable(reviewId, userDetails.getUser());
        placeService.delete(reviewId);
    }

    @PostMapping("/{placeId}/like")
    public void createOrUpdateLike(@PathVariable Long placeId, @CurrentUser CustomUserDetails userDetails) {
        placeService.createOrDeleteLike(placeId, userDetails.getUser());
    }
}
