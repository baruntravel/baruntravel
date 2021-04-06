package me.travelplan.service.route;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RouteCustomRepository {
    Page<Route> findAllByCoordinate(Double maxX, Double minX, Double maxY, Double minY, Pageable pageable);
}
