package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RouteRepositoryCustom {
    Page<Route> findAllByCoordinate(Double maxX, Double minX, Double maxY, Double minY, Pageable pageable);
    Optional<Route> findByIdWithUser(@Param("id") Long id);
}
