package me.travelplan.service.route;

import me.travelplan.service.file.File;
import me.travelplan.service.file.FileServer;
import me.travelplan.service.file.FileType;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceCategory;
import me.travelplan.service.user.User;
import me.travelplan.web.common.FileDto;
import me.travelplan.web.route.RouteDto;
import me.travelplan.web.route.RouteRequest;
import me.travelplan.web.route.RouteResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RouteMapper {
    RouteResponse.RouteId toRouteIdResponse(Route route);

    Route toEntity(RouteRequest.CreateEmpty request);

    default Place toPlace(RouteRequest.AddPlace request) {
        return Place.builder()
                .id(request.getPlace().getId())
                .name(request.getPlace().getName())
                .url(request.getPlace().getName())
                .category(PlaceCategory.builder().id(request.getPlace().getCategory()).build())
                .thumbnail(File.builder()
                        .name(request.getPlace().getName())
                        .extension("")
                        .height(0)
                        .width(0)
                        .server(FileServer.EXTERNAL)
                        .size(0L).type(FileType.IMAGE)
                        .url(request.getPlace().getImage())
                        .build())
                .x(request.getPlace().getX())
                .y(request.getPlace().getY())
                .build();
    }

    default Route toEntity(RouteRequest.CreateOrUpdate request, Long id) {
        var routeBuilder = Route.builder()
                .name(request.getName());

        if (id != 0L) {
            routeBuilder.id(id);
        }

        Route route = routeBuilder.build();

        request.getPlaces().forEach((place) -> route.addPlace(RoutePlace.builder().order(place.getOrder()).place(
                Place.builder()
                        .thumbnail(
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
                        .x(place.getX())
                        .y(place.getY())
                        .build()
        ).build()));

        return route;
    }

    default RouteResponse.GetOne toGetOneResponse(Route route) {
        List<RouteDto.RoutePlace> routePlaces = new ArrayList<>();

        route.getRoutePlaces().forEach(routePlace -> {
            var routePlaceBuilder = RouteDto.RoutePlace.builder();
            routePlaceBuilder.order(routePlace.getOrder());

            Place place = routePlace.getPlace();
            RouteDto.RoutePlace.RoutePlaceBuilder builder = routePlaceBuilder
                    .id(place.getId())
                    .name(place.getName())
                    .x(place.getX())
                    .y(place.getY())
                    .address(place.getAddress())
                    .category(place.getCategory().getName())
                    .url(place.getUrl());
            //TODO 현재 구현에는 place에 image가 null로 들어가고 테스트에서는 image가 들어가기 때문에 조건문처리를 해놓음
            // place에 image가 확실히 들어가게 되면 조건문 제거
            if (place.getThumbnail() != null) {
                routePlaces.add(builder.image(place.getThumbnail().getUrl()).build());
            } else {
                routePlaces.add(builder.build());
            }
        });
        HashMap<String, Double> map = route.calculateCenterCoordinate();
        Double centerX = map.get("centerX");
        Double centerY = map.get("centerY");

        return RouteResponse.GetOne.builder()
                .name(route.getName())
                .centerX(centerX)
                .centerY(centerY)
                .createdBy(route.getCreatedBy().getName())
                .createdAt(route.getCreatedAt())
                .updatedAt(route.getUpdatedAt())
                .reviewCount(route.getRouteReviews().size())
                .places(routePlaces)
                .build();
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

    default List<RouteResponse.GetList> toGetListResponse(List<Route> routes, User loginUser) {
        List<RouteResponse.GetList> getList = new ArrayList<>();
        routes.forEach(route -> {
            List<RouteDto.RoutePlace> routePlaces = new ArrayList<>();
            route.getRoutePlaces().forEach(routePlace -> {
                RouteDto.RoutePlace.RoutePlaceBuilder routePlaceBuilder = RouteDto.RoutePlace.builder()
                        .id(routePlace.getPlace().getId())
                        .name(routePlace.getPlace().getName())
                        .address(routePlace.getPlace().getAddress())
                        .x(routePlace.getPlace().getX())
                        .y(routePlace.getPlace().getY())
                        .category(routePlace.getPlace().getCategory().getName())
                        .url(routePlace.getPlace().getUrl())
                        .order(routePlace.getOrder());
                //TODO 현재 구현에는 place에 image가 null로 들어가고 테스트에서는 image가 들어가기 때문에 조건문처리를 해놓음
                // place에 image가 확실히 들어가게 되면 조건문 제거
                if (routePlace.getPlace().getThumbnail() != null) {
                    routePlaces.add(routePlaceBuilder.image(routePlace.getPlace().getThumbnail().getUrl()).build());
                } else {
                    routePlaces.add(routePlaceBuilder.build());
                }
            });
            HashMap<String, Double> map = route.calculateCenterCoordinate();
            Double centerX = map.get("centerX");
            Double centerY = map.get("centerY");

            RouteResponse.GetList list = RouteResponse.GetList.builder()
                    .id(route.getId())
                    .name(route.getName())
                    .centerX(centerX)
                    .centerY(centerY)
                    .likeCheck(route.isLike(loginUser))
                    .likeCount(route.getRouteLikes().size())
                    .createdBy(route.getCreatedBy().getName())
                    .places(routePlaces)
                    .build();
            getList.add(list);
        });
        return getList;
    }

    default RouteResponse.ReviewList entityToResponseReviewList(List<RouteReview> reviews, User user) {
        return RouteResponse.ReviewList.builder()
                .reviews(reviews.stream().map(routeReview -> RouteDto.ReviewResponse.builder()
                        .id(routeReview.getId())
                        .content(routeReview.getContent())
                        .score(routeReview.getScore())
                        .likeCheck(routeReview.isLike(user))
                        .likeCount(routeReview.getRouteReviewLikes().size())
                        .createdBy(routeReview.getCreatedBy().getName())
                        .files(routeReview.getRouteReviewFiles().stream()
                                .map(routeReviewFile -> FileDto.builder()
                                        .name(routeReviewFile.getFile().getName())
                                        .url(routeReviewFile.getFile().getUrl())
                                        .build())
                                .collect(Collectors.toList()))
                        .createdAt(routeReview.getCreatedAt())
                        .updatedAt(routeReview.getUpdatedAt())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
