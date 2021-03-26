package me.travelplan.service.route;

import lombok.AllArgsConstructor;
import me.travelplan.service.file.FileRepository;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final PlaceRepository placeRepository;
    private final FileRepository fileRepository;

    @Transactional
    public Route put(Route route) {
        fileRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).map(Place::getImage).collect(Collectors.toList()));
        placeRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).collect(Collectors.toList()));
        return routeRepository.save(route);
    }

    public Route getOne(Long id) {
        return routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
    }
}
