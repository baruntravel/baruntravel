package me.travelplan.service.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.exception.PermissionDeniedException;
import me.travelplan.service.file.repository.FileRepository;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.route.domain.Route;
import me.travelplan.service.route.domain.RouteLike;
import me.travelplan.service.route.domain.RoutePlace;
import me.travelplan.service.route.exception.RouteNotFoundException;
import me.travelplan.service.route.repository.RouteLikeRepository;
import me.travelplan.service.route.repository.RoutePlaceRepository;
import me.travelplan.service.route.repository.RouteRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.route.dto.RouteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final RoutePlaceRepository routePlaceRepository;
    private final PlaceRepository placeRepository;
    private final FileRepository fileRepository;
    private final RouteLikeRepository routeLikeRepository;

    @Transactional(readOnly = true)
    public List<Route> getByUser(User user) {
        return routeRepository.findByCreatedBy(user);
    }

    public Route create(RouteRequest.Create request) {
        List<RoutePlace> routePlaces = request.getPlaces().stream()
                .map(place -> {
                    Place findPlace = placeRepository.findById(place.getId()).orElseThrow(PlaceNotFoundException::new);
                    return RoutePlace.create(findPlace, place.getOrder());
                }).collect(Collectors.toList());
        Route route = Route.create(request.getName(), routePlaces);
        route.calculateCoordinate(routePlaces);

        return routeRepository.save(route);
    }

    public void updatePlaceOrder(Long id, RouteRequest.Update request, User user) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        if (!route.getCreatedBy().getId().equals(user.getId())) {
            throw new PermissionDeniedException();
        }
        RoutePlace firstRoutePlace = routePlaceRepository.findByRouteIdAndPlaceId(id, request.getFirstPlaceId()).orElseThrow();
        RoutePlace secondRoutePlace = routePlaceRepository.findByRouteIdAndPlaceId(id, request.getSecondPlaceId()).orElseThrow();
        RoutePlace.swapOrder(firstRoutePlace, secondRoutePlace);
    }

    @Transactional(readOnly = true)
    public Route getOne(Long id) {
        return routeRepository.findByIdWithUser(id).orElseThrow(RouteNotFoundException::new);
    }

    public Route addPlace(Long id, Place place) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        fileRepository.save(place.getThumbnail());
        route.addPlace(RoutePlace.builder().order(0).route(route).place(place).build());
        route.calculateCoordinate(route.getRoutePlaces());
        return routeRepository.save(route);
    }

    @Transactional(readOnly = true)
    public Page<Route> getList(RouteRequest.GetList request, Pageable pageable) {
        return routeRepository.findAllByCoordinate(request.getMaxX(), request.getMinX(), request.getMaxY(), request.getMinY(), pageable);
    }

    public void createOrDeleteLike(Long id, User user) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        Optional<RouteLike> optionalRouteLike = routeLikeRepository.findByRouteIdAndCreatedBy(id, user);
        if (optionalRouteLike.isEmpty()) {
            routeLikeRepository.save(RouteLike.create(route));
        }
        if (optionalRouteLike.isPresent()) {
            RouteLike routeLike = optionalRouteLike.get();
            routeLikeRepository.delete(routeLike);
        }
    }
}
