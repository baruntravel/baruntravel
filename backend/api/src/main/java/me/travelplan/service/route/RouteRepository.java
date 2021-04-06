package me.travelplan.service.route;

import me.travelplan.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> ,RouteCustomRepository{
    List<Route> findByCreatedBy(User user);

}
