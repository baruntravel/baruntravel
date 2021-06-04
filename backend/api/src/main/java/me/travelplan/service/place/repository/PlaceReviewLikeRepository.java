package me.travelplan.service.place.repository;

import me.travelplan.service.place.domain.PlaceReviewLike;
import me.travelplan.service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceReviewLikeRepository extends JpaRepository<PlaceReviewLike, Long> {
    Optional<PlaceReviewLike> findByPlaceReviewIdAndCreatedBy(Long placeReviewId, User user);
}
