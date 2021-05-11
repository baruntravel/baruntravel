package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.RouteReview;

import java.util.List;

public interface RouteReviewRepositoryCustom {
    List<RouteReview> findAllByRouteId(Long routeId);
}
