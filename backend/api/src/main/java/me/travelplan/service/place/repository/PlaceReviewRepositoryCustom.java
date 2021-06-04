package me.travelplan.service.place.repository;

import me.travelplan.service.place.domain.PlaceReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceReviewRepositoryCustom {
    Page<PlaceReview> findAllByPlaceId(Long placeId, String sortType, Pageable pageable);
}
