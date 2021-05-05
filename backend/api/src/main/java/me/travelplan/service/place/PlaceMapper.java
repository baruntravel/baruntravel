package me.travelplan.service.place;

import me.travelplan.service.user.User;
import me.travelplan.web.common.FileDto;
import me.travelplan.web.place.PlaceDto;
import me.travelplan.web.place.PlaceRequest;
import me.travelplan.web.place.PlaceResponse;
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

    @Mapping(source = "review", target = ".")
    PlaceReview requestToEntity(PlaceRequest.PutReview request);

    @Mapping(source = "request.review.score", target = "score")
    @Mapping(source = "request.review.content", target = "content")
    @Mapping(source = "reviewId", target = "id")
    PlaceReview requestToEntity(Long reviewId, PlaceRequest.PutReview request);

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
                .files(place.getImages().stream().map(placeImage -> FileDto.builder().url(placeImage.getFile().getUrl()).build()).collect(Collectors.toList()))
                .likeCount(place.getPlaceLikes().size())
                .likeCheck(place.isLike(user))
                .build();
    }
}
