package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.RoutePlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoutePlaceRepository extends JpaRepository<RoutePlace, Long> {
    Optional<RoutePlace> findByRouteIdAndPlaceId(Long id, Long placeId);
}
