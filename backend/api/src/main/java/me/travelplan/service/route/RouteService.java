package me.travelplan.service.route;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class RouteService {
    private final RouteRepository routeRepository;

    @Transactional
    public Route put(Route route) {
        return routeRepository.save(route);
    }
}
