package me.travelplan.web.place;

import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceImage;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.common.FileDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PlaceMapper {
    default PlaceDto.Response entityToResponseDto(Place place, User currentUser) {
        return PlaceDto.Response.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .x(place.getX())
                .y(place.getY())
                .categoryId(place.getCategory().getId())
                .categoryName(place.getCategory().getName())
                .url(place.getUrl())
                .thumbnailUrl(Optional.ofNullable(place.getThumbnail()).orElse(File.createExternalImage("")).getUrl())
                .images(place.getImages().stream().map(PlaceImage::getFile).map(File::getUrl).map(FileDto.Image::new).collect(Collectors.toList()))
                .isLike(place.isLike(currentUser))
                .likes(place.getPlaceLikes().size())
                .score(place.getAverageReviewScore())
                .openHour(place.getOpenHour())
                .build();
    }
}
