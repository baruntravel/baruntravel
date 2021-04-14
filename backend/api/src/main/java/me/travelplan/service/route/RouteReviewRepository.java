package me.travelplan.service.route;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteReviewRepository extends JpaRepository<RouteReview, Long> {
    List<RouteReview> findAllByRouteId(Long routeId);
}
