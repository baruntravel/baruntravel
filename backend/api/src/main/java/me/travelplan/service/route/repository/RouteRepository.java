package me.travelplan.service.route.repository;

import me.travelplan.service.route.domain.Route;
import me.travelplan.service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long>, RouteRepositoryCustom {
    List<Route> findByCreatedBy(User user);

//    @Query("select r from Route r join fetch r.createdBy where r.id=:id and r.deletedAt is null")
//    Optional<Route> findByIdWithUser(@Param("id") Long id);
}
