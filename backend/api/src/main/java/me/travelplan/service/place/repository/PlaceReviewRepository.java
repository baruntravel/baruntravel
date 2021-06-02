package me.travelplan.service.place.repository;

import me.travelplan.service.place.domain.PlaceReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceReviewRepository extends JpaRepository<PlaceReview, Long> ,PlaceReviewRepositoryCustom{
}
