package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.file.FileService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.place.domain.PlaceReviewImage;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotUpdatableException;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.place.repository.PlaceReviewImageRepository;
import me.travelplan.service.place.repository.PlaceReviewRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.place.review.PlaceReviewDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceReviewService {
    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceReviewImageRepository placeReviewImageRepository;
    private final FileService fileService;

    public List<PlaceReview> getReviews(Long placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
        return place.getReviews();
    }

    @Transactional
    public PlaceReview createReview(Long placeId, PlaceReviewDto.Request reviewDto) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);

        List<PlaceReviewImage> images = new ArrayList<>();
        if (!reviewDto.getImages().isEmpty()) {
            images = fileService.uploadFiles(reviewDto.getImages())
                            .stream().map(file -> PlaceReviewImage.builder().file(file).build())
                            .collect(Collectors.toList());
            images = placeReviewImageRepository.saveAll(images);
        }

        PlaceReview review = PlaceReview.from(reviewDto, place, images);
        return placeReviewRepository.save(review);
    }

    @Transactional
    public PlaceReview updateReview(Long reviewId, PlaceReviewDto.Request reviewDto) {
        PlaceReview review = placeReviewRepository.findById(reviewId).orElseThrow(PlaceReviewNotFoundException::new);

        List<PlaceReviewImage> images = new ArrayList<>();
        if (!reviewDto.getImages().isEmpty()) {
            images = fileService.uploadFiles(reviewDto.getImages())
                    .stream().map(file -> PlaceReviewImage.builder().file(file).review(review).build())
                    .collect(Collectors.toList());
            images = placeReviewImageRepository.saveAll(images);
        }
        review.update(reviewDto, images);

        return placeReviewRepository.save(review);
    }

    @Transactional
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
