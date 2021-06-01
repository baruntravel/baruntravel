package me.travelplan.web.place.review;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.place.PlaceReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/place/{placeId}/review")
public class PlaceReviewController {
    private final PlaceReviewService placeReviewService;
    private final PlaceReviewMapper placeReviewMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PlaceReviewDto.Response> getReviews(@PathVariable Long placeId, @CurrentUser CustomUserDetails userDetails) {
        return placeReviewMapper.entityToResponseDto(placeReviewService.getReviews(placeId), userDetails.getUser());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PlaceReviewDto.GetOnlyId create(@PathVariable Long placeId, PlaceReviewDto.Request placeReviewDto) {
        return placeReviewMapper.toReviewId(placeReviewService.createReview(placeId, placeReviewDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{reviewId}")
    public PlaceReviewDto.Response update(@PathVariable Long reviewId, PlaceReviewDto.Request placeReviewDto, @CurrentUser CustomUserDetails userDetails) {
        placeReviewService.checkReviewUpdatable(reviewId, userDetails.getUser());
        return placeReviewMapper.entityToResponseDto(placeReviewService.updateReview(reviewId, placeReviewDto), userDetails.getUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{reviewId}")
    public void delete(@PathVariable Long reviewId, @CurrentUser CustomUserDetails userDetails) {
        placeReviewService.checkReviewUpdatable(reviewId, userDetails.getUser());
        placeReviewService.deleteReview(reviewId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{reviewId}/image/{reviewImageId}")
    public void deleteImage(@PathVariable Long reviewId, @PathVariable Long reviewImageId, @CurrentUser CustomUserDetails userDetails) {
        placeReviewService.checkReviewUpdatable(reviewId, userDetails.getUser());
        placeReviewService.deleteReviewImage(reviewImageId);
    }
}
