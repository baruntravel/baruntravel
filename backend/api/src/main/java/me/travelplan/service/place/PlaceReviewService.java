package me.travelplan.service.place;

import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.place.exception.PlaceReviewNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotUpdatableException;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.place.repository.PlaceReviewRepository;
import me.travelplan.service.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class PlaceReviewService {
    private PlaceRepository placeRepository;
    private PlaceReviewRepository placeReviewRepository;

    public PlaceReview createReview(PlaceReview review) {
        return placeReviewRepository.save(review);
    }

    public PlaceReview updateReview(PlaceReview review) {
        return placeReviewRepository.save(review);
    }

    public void deleteReview(Long placeReviewId) {
        placeReviewRepository.deleteById(placeReviewId);
    }

    public void checkReviewUpdatable(Long placeReviewId, User user) {
        PlaceReview review = placeReviewRepository.findById(placeReviewId).orElseThrow(PlaceReviewNotFoundException::new);

        if (!review.getCreatedBy().getId().equals(user.getId())) {
            throw new PlaceReviewNotUpdatableException();
        }
    }
}
