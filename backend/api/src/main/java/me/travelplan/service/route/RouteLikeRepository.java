package me.travelplan.service.route;

import me.travelplan.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteLikeRepository extends JpaRepository<RouteLike,Long> {
    Optional<RouteLike> findByRouteIdAndCreatedBy(Long routeId, User user);
}
