package me.travelplan.service.route;

import me.travelplan.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteReviewLikeRepository extends JpaRepository<RouteReviewLike, Long> {
    Optional<RouteReviewLike> findByRouteReviewIdAndCreatedBy(Long id, User user);
}
