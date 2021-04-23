package me.travelplan.service.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartPlaceRepository extends JpaRepository<CartPlace, Long> {

    Optional<CartPlace> findByPlaceId(Long placeId);
}
