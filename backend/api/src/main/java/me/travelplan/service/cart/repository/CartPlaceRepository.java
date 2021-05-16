package me.travelplan.service.cart.repository;

import me.travelplan.service.cart.domain.CartPlace;
import me.travelplan.service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartPlaceRepository extends JpaRepository<CartPlace, Long>, CartPlaceRepositoryCustom {
    Optional<CartPlace> findByPlaceIdAndCreatedBy(Long placeId, User user);

    void deleteByPlaceIdAndCreatedBy(Long placeId, User user);

    void deleteAllByCreatedBy(User user);
}
