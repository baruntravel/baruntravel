package me.travelplan.web.place.review;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.place.PlaceReviewService;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.web.common.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/place/{placeId}/review")
public class PlaceReviewController {
    private final PlaceReviewService placeReviewService;
    private final PlaceReviewMapper placeReviewMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PlaceReviewDto.GetOnlyId create(@PathVariable Long placeId, PlaceReviewDto.Request placeReviewDto) {
        return placeReviewMapper.toReviewId(placeReviewService.createReview(placeId, placeReviewDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<PlaceReviewDto.Response> getReviews(@PathVariable Long placeId, PageDto pageDto, @CurrentUser CustomUserDetails userDetails) {
        List<PlaceReview> content = placeReviewService.getReviews(placeId, pageDto).getContent();
        List<PlaceReviewDto.Response> getList = placeReviewMapper.entityToResponseDto(content, userDetails.getUser());

        return new PageImpl<>(getList, pageDto.of(), content.size());
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{reviewId}/like")
    public void like(@PathVariable Long reviewId, @CurrentUser CustomUserDetails userDetails) {
        placeReviewService.createOrDeleteLike(reviewId, userDetails.getUser());
    }
}
