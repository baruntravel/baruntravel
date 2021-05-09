package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.Route;
import me.travelplan.service.route.domain.RoutePlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutePlaceRepository extends JpaRepository<RoutePlace, Long> {
    void deleteAllByRoute(Route route);
}
