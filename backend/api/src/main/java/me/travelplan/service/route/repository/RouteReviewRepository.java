package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.RouteReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteReviewRepository extends JpaRepository<RouteReview, Long> {
    @Query("select rr from RouteReview rr join fetch rr.createdBy where rr.route.id=:routeId and rr.deletedAt is null")
    List<RouteReview> findAllByRouteId(@Param("routeId") Long routeId);
}
