package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.exception.PlaceNotUpdatableException;
import me.travelplan.service.place.exception.PlaceReviewNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotUpdatableException;
import me.travelplan.service.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;

    @Transactional
    public PlaceReview createReview(Long placeId, PlaceReview placeReview) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
        placeReview.setPlace(place);
        return placeReviewRepository.save(placeReview);
    }

    public PlaceReview updateReview(PlaceReview changed) {
        PlaceReview before = placeReviewRepository.findById(changed.getId()).orElseThrow(PlaceReviewNotFoundException::new);
        before.update(changed);
        return placeReviewRepository.save(before);
    }

    public void checkUpdatable(Long placeId, User user) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);

        if (!place.getCreatedBy().getId().equals(user.getId())) {
            throw new PlaceNotUpdatableException();
        }
    }

    public void checkReviewUpdatable(Long placeReviewId, User user) {
        PlaceReview review = placeReviewRepository.findById(placeReviewId).orElseThrow(PlaceReviewNotFoundException::new);

        if (!review.getCreatedBy().getId().equals(user.getId())) {
            throw new PlaceReviewNotUpdatableException();
        }
    }
}
