package me.travelplan.service.route;

import lombok.AllArgsConstructor;
import me.travelplan.service.file.FileRepository;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceRepository;
import me.travelplan.service.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RoutePlaceRepository routePlaceRepository;
    private final PlaceRepository placeRepository;
    private final FileRepository fileRepository;

    @Transactional
    public Route createEmpty(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> getByUser(User user) {
        return routeRepository.findByCreatedBy(user);
    }

    @Transactional
    public Route create(Route route) {
        fileRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).map(Place::getImage).collect(Collectors.toList()));
        placeRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).collect(Collectors.toList()));
        return routeRepository.save(route);
    }

    @Transactional
    public Route update(Route route) {
        routePlaceRepository.deleteAllByRoute(route);
        fileRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).map(Place::getImage).collect(Collectors.toList()));
        placeRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).collect(Collectors.toList()));
        return routeRepository.save(route);
    }

    public Route getOne(Long id) {
        return routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
    }

    public Route addPlace(Long id, Place place) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        fileRepository.save(place.getImage());
        route.addPlace(RoutePlace.builder().order(0).route(route).place(placeRepository.save(place)).build());
        route.calculateCenterCoordinate();
        return routeRepository.save(route);
    }
}
