package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.RouteReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RouteReviewRepositoryCustom {
    Page<RouteReview> findAllByRouteId(Long routeId, String sortType, Pageable pageable);
}
