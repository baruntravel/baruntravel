package me.travelplan.service.route;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteReviewFileRepository extends JpaRepository<RouteReviewFile, Long> {
    void deleteAllByRouteReviewId(Long routeReviewId);
}
