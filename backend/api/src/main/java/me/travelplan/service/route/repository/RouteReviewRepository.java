package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.RouteReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteReviewRepository extends JpaRepository<RouteReview, Long>, RouteReviewRepositoryCustom {

}
