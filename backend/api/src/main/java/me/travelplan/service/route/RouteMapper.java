package me.travelplan.service.route;

import me.travelplan.service.file.File;
import me.travelplan.service.file.FileServer;
import me.travelplan.service.file.FileType;
import me.travelplan.service.place.Place;
import me.travelplan.web.route.RouteDto;
import me.travelplan.web.route.RouteRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RouteMapper {
    default Route toEntity(RouteRequest.Put request) {
        Route route = Route.builder()
                .name(request.getName())
                .x(request.getPlaces().stream().mapToDouble(RouteDto.RoutePlace::getX).average().getAsDouble())
                .y(request.getPlaces().stream().mapToDouble(RouteDto.RoutePlace::getY).average().getAsDouble())
                .build();

        request.getPlaces().forEach((place) -> {
            route.addPlace(RoutePlace.builder().order(place.getOrder()).place(
                    Place.builder()
                            .image(
                                    File.builder()
                                            .name(place.getName())
                                            .extension("")
                                            .height(0)
                                            .width(0)
                                            .server(FileServer.EXTERNAL)
                                            .size(0L).type(FileType.IMAGE)
                                            .url(place.getImage())
                                            .build()
                            )
                            .thirdId(place.getThirdId())
                            .url(place.getUrl())
                            .name(place.getName())
                            .x(place.getX())
                            .y(place.getY())
                            .build()
            ).build());
        });

        return route;
    }
}
