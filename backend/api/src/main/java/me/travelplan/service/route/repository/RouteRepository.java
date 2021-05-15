package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.Route;
import me.travelplan.service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long>, RouteRepositoryCustom {
    List<Route> findByCreatedBy(User user);
}
