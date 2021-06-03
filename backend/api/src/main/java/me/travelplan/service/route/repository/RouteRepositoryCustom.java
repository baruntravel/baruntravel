package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RouteRepositoryCustom {
    Page<Route> findAllByCoordinate(Double maxX, Double minX, Double maxY, Double minY, String sortType, Pageable pageable);

    Page<Route> findAllByRegion(String region, String sortType, Pageable pageable);

    Optional<Route> findByIdWithUser(Long id);
}
