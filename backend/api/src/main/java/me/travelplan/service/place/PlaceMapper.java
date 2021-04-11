package me.travelplan.service.place;

import me.travelplan.service.user.User;
import me.travelplan.web.place.PlaceDto;
import me.travelplan.web.place.PlaceRequest;
import me.travelplan.web.place.PlaceResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PlaceMapper {
    PlaceReview dtoToEntity(PlaceDto.ReviewRequest review);

    @Mapping(source = "review", target = ".")
    PlaceReview requestToEntity(PlaceRequest.PutReview request);

    @Mapping(source = "request.review.score", target = "score")
    @Mapping(source = "request.review.content", target = "content")
    @Mapping(source = "reviewId", target = "id")
    PlaceReview requestToEntity(Long reviewId, PlaceRequest.PutReview request);

    PlaceDto.ReviewResponse entityToDto(PlaceReview review);

    @Mapping(source = ".", target = "review")
    PlaceResponse.ReviewOne entityToResponseReviewOne(PlaceReview review);
}
