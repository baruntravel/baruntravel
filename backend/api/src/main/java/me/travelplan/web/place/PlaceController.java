package me.travelplan.web.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.place.PlaceMapper;
import me.travelplan.service.place.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/place")
@RestController
public class PlaceController {
    private final PlaceMapper placeMapper;
    private final PlaceService placeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{placeId}/review")
    public void createReview(@PathVariable Long placeId, @RequestBody PlaceRequest.PutReview request) {
        placeService.createReview(placeId, placeMapper.requestToEntity(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{placeId}/review/{reviewId}")
    public PlaceResponse.ReviewOne updateReview(@PathVariable Long reviewId, @RequestBody PlaceRequest.PutReview request, @CurrentUser CustomUserDetails userDetails) {
        placeService.checkReviewUpdatable(reviewId, userDetails.getUser());
        return placeMapper.entityToResponseReviewOne(placeService.updateReview(placeMapper.requestToEntity(reviewId, request)));
    }
}
