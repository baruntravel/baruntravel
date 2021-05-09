package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.RouteReviewLike;
import me.travelplan.service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteReviewLikeRepository extends JpaRepository<RouteReviewLike, Long> {
    Optional<RouteReviewLike> findByRouteReviewIdAndCreatedBy(Long id, User user);
}
