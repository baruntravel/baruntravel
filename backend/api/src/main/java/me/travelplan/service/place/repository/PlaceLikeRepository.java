package me.travelplan.service.place.repository;

import me.travelplan.service.place.domain.PlaceLike;
import me.travelplan.service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceLikeRepository extends JpaRepository<PlaceLike, Long> {
    Optional<PlaceLike> findByPlaceIdAndCreatedBy(Long placeId, User user);
}
