package me.travelplan.web.place;

import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.common.FileDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PlaceMapper {


    PlaceReview dtoToEntity(PlaceDto.ReviewRequest review);

    @Mapping(source = "placeId", target = "id")
    @Mapping(source = "request.review.score", target = "score")
    @Mapping(source = "request.review.content", target = "content")
    PlaceReview placeIdAndRequestToEntity(Long placeId, PlaceRequest.PutReview request);

    @Mapping(source = "reviewId", target = "id")
    @Mapping(source = "request.review.score", target = "score")
    @Mapping(source = "request.review.content", target = "content")
    PlaceReview reviewIdAndRequestToEntity(Long reviewId, PlaceRequest.PutReview request);

    PlaceDto.ReviewResponse entityToDto(PlaceReview review);

    List<PlaceDto.ReviewResponse> entityToDto(List<PlaceReview> reviews);

    @Mapping(source = ".", target = "review")
    PlaceResponse.Review entityToResponseReview(PlaceReview review);

    default PlaceResponse.GetOne entityToGetOneDto(Place place, User user) {
        return PlaceResponse.GetOne.builder()
                .name(place.getName())
                .address(place.getAddress())
                .x(place.getX())
                .y(place.getY())
                .category(place.getCategory().getName())
                .openHour(place.getOpenHour())
                .images(place.getImages().stream().map(placeImage -> FileDto.builder().url(placeImage.getFile().getUrl()).build()).collect(Collectors.toList()))
                .score(place.getAverageReviewScore())
                .likeCount(place.getPlaceLikes().size())
                .likeCheck(place.isLike(user))
                .build();
    }
}
