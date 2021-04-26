package me.travelplan.service.place;

import me.travelplan.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceLikeRepository extends JpaRepository<PlaceLike, Long> {
    Optional<PlaceLike> findByPlaceIdAndCreatedBy(Long placeId, User user);
}
