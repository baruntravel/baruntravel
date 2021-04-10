package me.travelplan.service.place;

import me.travelplan.web.place.PlaceDto;
import me.travelplan.web.place.PlaceRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PlaceMapper {
    PlaceReview dtoToEntity(PlaceDto.Review review);

    @Mapping(target = ".", source = "review")
    PlaceReview requestToEntity(PlaceRequest.CreateReview request);
}
