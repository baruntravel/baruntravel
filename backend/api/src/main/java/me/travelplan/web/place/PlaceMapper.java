package me.travelplan.web.place;

import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceImage;
import me.travelplan.web.common.FileDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PlaceMapper {
    @Mapping(constant = "PENDING", target = "detailStatus")
    @Mapping(source = "place.categoryId", target = "category.id")
    @Mapping(constant = "", target = "openHour")
    Place dtoToPlace(PlaceDto.Request place);

    default PlaceDto.Response entityToResponseDto(Place place, CustomUserDetails customUserDetails) {
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
                .isLike(place.isLike(customUserDetails))
                .likes(place.getPlaceLikes().size())
                .score(place.getAverageReviewScore())
                .openHour(place.getOpenHour())
                .build();
    }
}
