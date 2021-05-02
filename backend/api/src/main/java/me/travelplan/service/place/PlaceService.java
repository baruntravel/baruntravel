package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.file.File;
import me.travelplan.service.file.FileRepository;
import me.travelplan.service.kakaomap.KakaoMapPlace;
import me.travelplan.service.kakaomap.KakaoMapService;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.exception.PlaceNotUpdatableException;
import me.travelplan.service.place.exception.PlaceReviewNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotUpdatableException;
import me.travelplan.service.user.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceLikeRepository placeLikeRepository;
    private final KakaoMapService kakaoMapService;
    private final FileRepository fileRepository;
    private final PlaceImageRepository placeImageRepository;

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

    @Transactional
    public PlaceReview updateReview(PlaceReview changed) {
        PlaceReview before = placeReviewRepository.findById(changed.getId()).orElseThrow(PlaceReviewNotFoundException::new);
        before.update(changed);
        return placeReviewRepository.save(before);
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

    @Async
    @Transactional
    public void updateDetail(Long id) {
        Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        KakaoMapPlace kakaoPlace = kakaoMapService.getKakaoMapPlace(id);

        List<File> images = kakaoPlace.getPhotos().stream()
                .map(photo -> File.createExternalImage(photo.getImageUrl()))
                .collect(Collectors.toList());

        images = fileRepository.saveAll(images);

        List<PlaceImage> placeImages = images.stream()
                .map(image -> PlaceImage.builder().place(place).file(image).build())
                .collect(Collectors.toList());
        placeImageRepository.saveAll(placeImages);

        place.setFromKakaoMapPlace(kakaoPlace);
        placeRepository.save(place);
    }
}
