package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;

    @Transactional
    public PlaceReview createReview(Long placeId, PlaceReview placeReview) {
        Place place = placeRepository.findById(placeId).orElseThrow();

        placeReview.setPlace(place);
        return placeReviewRepository.save(placeReview);
    }
}
