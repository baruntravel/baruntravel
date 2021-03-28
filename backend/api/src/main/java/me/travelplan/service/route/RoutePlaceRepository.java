package me.travelplan.service.route;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutePlaceRepository extends JpaRepository<RoutePlace, Long> {
    void deleteAllByRoute(Route route);
}
