package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.travelplan.component.kakaomap.KakaoMapPlace;
import me.travelplan.component.kakaomap.KakaoMapService;
import me.travelplan.service.file.repository.FileRepository;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceLike;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.exception.PlaceNotUpdatableException;
import me.travelplan.service.place.exception.PlaceReviewNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotUpdatableException;
import me.travelplan.service.place.repository.PlaceImageRepository;
import me.travelplan.service.place.repository.PlaceLikeRepository;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.place.repository.PlaceReviewRepository;
import me.travelplan.service.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceLikeRepository placeLikeRepository;
    private final KakaoMapService kakaoMapService;
    private final FileRepository fileRepository;
    private final PlaceImageRepository placeImageRepository;
    private final PlaceReviewService placeReviewService;
    private final PlaceLikeService placeLikeService;

    /*************************************
     *              Place
     *************************************/

    public Place getOne(Long id) {
        return placeRepository.findByIdWithCategory(id).orElseThrow(PlaceNotFoundException::new);
    }

    @Transactional
    public Place create(Place place) {
        Place savedPlace = placeRepository.save(place);
        this.updateDetail(savedPlace.getId());
        return savedPlace;
    }

    @Transactional
    public void delete(Long reviewId) {
        placeReviewRepository.deleteById(reviewId);
    }

    public void checkUpdatable(Long placeId, User user) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);

        if (!place.getCreatedBy().getId().equals(user.getId())) {
            throw new PlaceNotUpdatableException();
        }
    }

    //    @Async
    private void updateDetail(Long id) {
        try {
            Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
            KakaoMapPlace kakaoPlace = kakaoMapService.getKakaoMapPlace(id).orElseThrow();
            place.setFromKakaoMapPlace(kakaoPlace);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /*************************************
     *          Place Review
     *************************************/

    public List<PlaceReview> getReviews(Long placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
        return place.getReviews();
    }

    @Transactional
    public PlaceReview createReview(PlaceReview review) {
        return placeReviewService.createReview(review);
    }

    @Transactional
    public PlaceReview updateReview(PlaceReview after) {
        return placeReviewService.updateReview(after);
    }

    public void checkReviewUpdatable(Long placeReviewId, User user) {
        placeReviewService.checkReviewUpdatable(placeReviewId, user);
    }


    /*************************************
     *          Place Like
     *************************************/

    public void createOrDeleteLike(Long placeId, User user) {
        placeLikeService.createOrDeleteLike(placeId, user);
    }
}
