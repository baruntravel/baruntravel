package me.travelplan.service.cart;

import me.travelplan.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartPlaceRepository extends JpaRepository<CartPlace, Long> {
    Optional<CartPlace> findByPlaceIdAndCreatedBy(Long placeId, User user);

    @Query("select c from CartPlace c join fetch c.place where c.createdBy=:user")
    List<CartPlace> findAllByCreatedBy(@Param("user") User user);

    void deleteByPlaceIdAndCreatedBy(Long placeId, User user);
}
