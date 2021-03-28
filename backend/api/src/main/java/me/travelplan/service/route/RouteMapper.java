package me.travelplan.service.route;

import me.travelplan.service.file.File;
import me.travelplan.service.file.FileServer;
import me.travelplan.service.file.FileType;
import me.travelplan.service.place.Place;
import me.travelplan.web.route.RouteDto;
import me.travelplan.web.route.RouteRequest;
import me.travelplan.web.route.RouteResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RouteMapper {
    Route toEntity(RouteRequest.CreateEmpty request);

    default Route toEntity(RouteRequest.CreateOrUpdate request, Long id) {
        var routeBuilder = Route.builder()
                .name(request.getName())
                .x(request.getPlaces().stream().mapToDouble(RouteDto.RoutePlace::getX).average().getAsDouble())
                .y(request.getPlaces().stream().mapToDouble(RouteDto.RoutePlace::getY).average().getAsDouble());

        if (id != 0L) {
            routeBuilder.id(id);
        }

        Route route = routeBuilder.build();

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
                            .id(place.getId())
                            .url(place.getUrl())
                            .name(place.getName())
                            .x(place.getX())
                            .y(place.getY())
                            .build()
            ).build());
        });

        return route;
    }

    default RouteResponse.GetOne toGetOneResponse(Route route) {
        var response = RouteResponse.GetOne.builder();

        response.name(route.getName());
        response.x(route.getX());
        response.y(route.getY());

        List<RouteDto.RoutePlace> routePlaces = new ArrayList<>();

        route.getPlaces().forEach(routePlace -> {
            var routePlaceBuilder = RouteDto.RoutePlace.builder();
            routePlaceBuilder.order(routePlace.getOrder());

            Place place = routePlace.getPlace();
            routePlaces.add(routePlaceBuilder
                    .id(place.getId())
                    .name(place.getName())
                    .image(place.getImage().getUrl())
                    .x(place.getX())
                    .y(place.getY())
                    .url(place.getUrl())
                    .build());
        });
        response.places(routePlaces);

        return response.build();
    }

    default RouteResponse.GetsWithOnlyName toGetsWithOnlyNameResponse(List<Route> routes) {
        return RouteResponse.GetsWithOnlyName.builder()
                .routes(routes.stream().map(route -> RouteDto.RouteWithOnlyName.builder()
                        .id(route.getId())
                        .name(route.getName())
                        .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
