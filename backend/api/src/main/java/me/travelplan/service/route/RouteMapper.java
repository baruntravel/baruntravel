package me.travelplan.service.route;

import me.travelplan.service.file.File;
import me.travelplan.service.file.FileServer;
import me.travelplan.service.file.FileType;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceCategory;
import me.travelplan.web.route.RouteDto;
import me.travelplan.web.route.RouteRequest;
import me.travelplan.web.route.RouteResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RouteMapper {
    RouteResponse.RouteId toRouteIdResponse(Route route);
    Route toEntity(RouteRequest.CreateEmpty request);
    RouteReview toRouteReview(RouteRequest.CreateReview request);

    default Place toPlace(RouteRequest.AddPlace request) {
        return Place.builder()
                .id(request.getPlace().getId())
                .name(request.getPlace().getName())
                .url(request.getPlace().getName())
                .category(PlaceCategory.builder().id(request.getPlace().getCategory()).build())
                .image(File.builder()
                        .name(request.getPlace().getName())
                        .extension("")
                        .height(0)
                        .width(0)
                        .server(FileServer.EXTERNAL)
                        .size(0L).type(FileType.IMAGE)
                        .url(request.getPlace().getImage())
                        .build())
                .point(
                        (new GeometryFactory()).createPoint(new Coordinate(
                                request.getPlace().getX(),
                                request.getPlace().getY()
                        ))
                )
                .build();
    }

    default Route toEntity(RouteRequest.CreateOrUpdate request, Long id) {
        var routeBuilder = Route.builder()
                .name(request.getName());
//                .x(request.getPlaces().stream().mapToDouble(RouteDto.RoutePlace::getX).average().getAsDouble())
//                .y(request.getPlaces().stream().mapToDouble(RouteDto.RoutePlace::getY).average().getAsDouble());

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
                            .category(PlaceCategory.builder().id(place.getCategory()).build())
                            .id(place.getId())
                            .url(place.getUrl())
                            .name(place.getName())
                            .point(
                                    (new GeometryFactory()).createPoint(new Coordinate(
                                            place.getX(),
                                            place.getY()
                                    ))
                            )
                            .build()
            ).build());
        });

        return route;
    }

    default RouteResponse.GetOne toGetOneResponse(Route route) {
        var response = RouteResponse.GetOne.builder();

        response.name(route.getName());
        response.maxX(route.getMaxPoint().getX());
        response.minX(route.getMinPoint().getX());
        response.maxY(route.getMaxPoint().getY());
        response.minY(route.getMinPoint().getY());

        List<RouteDto.RoutePlace> routePlaces = new ArrayList<>();

        route.getPlaces().forEach(routePlace -> {
            var routePlaceBuilder = RouteDto.RoutePlace.builder();
            routePlaceBuilder.order(routePlace.getOrder());

            Place place = routePlace.getPlace();
            routePlaces.add(routePlaceBuilder
                    .id(place.getId())
                    .name(place.getName())
                    .image(place.getImage().getUrl())
                    .x(place.getPoint().getX())
                    .y(place.getPoint().getY())
                    .category(place.getCategory().getId())
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

    default List<RouteResponse.GetList> toGetListResponse(List<Route> routes) {
        List<RouteResponse.GetList> getList = new ArrayList<>();
        routes.stream().forEach(route -> {
            List<RouteDto.RoutePlace> routePlaces = new ArrayList<>();
            route.getPlaces().stream().forEach(routePlace -> {
                routePlaces.add(RouteDto.RoutePlace.builder()
                        .id(routePlace.getPlace().getId())
                        .name(routePlace.getPlace().getName())
                        .order(routePlace.getOrder())
                        .image(routePlace.getPlace().getImage().getUrl())
                        .x(routePlace.getPlace().getPoint().getX())
                        .y(routePlace.getPlace().getPoint().getY())
                        .category(routePlace.getPlace().getCategory().getId())
                        .url(routePlace.getPlace().getUrl())
                        .build());
            });
            getList.add(RouteResponse.GetList.builder().name(route.getName())
                    .places(routePlaces).build());
        });
        return getList;
    }


}
