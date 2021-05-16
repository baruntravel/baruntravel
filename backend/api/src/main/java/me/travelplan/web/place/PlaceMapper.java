package me.travelplan.web.place;

import me.travelplan.service.place.domain.Place;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.common.FileDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PlaceMapper {
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
