package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.exception.PlaceNotUpdatableException;
import me.travelplan.service.place.exception.PlaceReviewNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotUpdatableException;
import me.travelplan.service.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceLikeRepository placeLikeRepository;

    public List<PlaceReview> getReviews(Long placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
        return place.getReviews();
    }

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

    public void delete(Long reviewId) {
        placeReviewRepository.deleteById(reviewId);
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

    @Transactional
    public void createOrUpdateLike(Long placeId, User user) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
        Optional<PlaceLike> optionalPlaceLike = placeLikeRepository.findByPlaceIdAndCreatedBy(placeId, user);
        if (optionalPlaceLike.isEmpty()) {
            placeLikeRepository.save(PlaceLike.create(place));
        }
        if (optionalPlaceLike.isPresent()) {
            PlaceLike placeLike = optionalPlaceLike.get();
            placeLikeRepository.delete(placeLike);
        }
    }
}
