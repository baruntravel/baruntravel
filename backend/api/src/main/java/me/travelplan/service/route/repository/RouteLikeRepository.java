package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.RouteLike;
import me.travelplan.service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteLikeRepository extends JpaRepository<RouteLike,Long> {
    Optional<RouteLike> findByRouteIdAndCreatedBy(Long routeId, User user);
}
