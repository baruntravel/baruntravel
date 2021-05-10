package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.RouteReview;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteReviewRepositoryCustom {
    List<RouteReview> findAllByRouteId(@Param("routeId") Long routeId);
}
