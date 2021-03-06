package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.file.FileService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.place.domain.PlaceReviewImage;
import me.travelplan.service.place.domain.PlaceReviewLike;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotFoundException;
import me.travelplan.service.place.exception.PlaceReviewNotUpdatableException;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.place.repository.PlaceReviewImageRepository;
import me.travelplan.service.place.repository.PlaceReviewLikeRepository;
import me.travelplan.service.place.repository.PlaceReviewRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.common.PageDto;
import me.travelplan.web.place.review.PlaceReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceReviewService {
    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceReviewImageRepository placeReviewImageRepository;
    private final PlaceReviewLikeRepository placeReviewLikeRepository;
    private final FileService fileService;

    @Transactional
    public PlaceReview createReview(Long placeId, PlaceReviewDto.Request reviewDto) {
        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);

        List<PlaceReviewImage> images = new ArrayList<>();
        if (!reviewDto.getImages().isEmpty()) {
            images = fileService.uploadFiles(reviewDto.getImages())
                    .stream().map(file -> PlaceReviewImage.builder().file(file).build())
                    .collect(Collectors.toList());
        }

        PlaceReview review = PlaceReview.from(reviewDto, place, images);
        return placeReviewRepository.save(review);
    }

    public Page<PlaceReview> getReviews(Long placeId, PageDto pageDto) {
        return placeReviewRepository.findAllByPlaceId(placeId, pageDto.getSortType(), pageDto.of());
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

        return review;
    }

    @Transactional
    public void deleteReview(Long placeReviewId) {
        placeReviewRepository.deleteById(placeReviewId);
    }

    @Transactional
    public void deleteReviewImage(Long placeReviewImageId) {
        placeReviewImageRepository.deleteById(placeReviewImageId);
    }

    @Transactional
    public void createOrDeleteLike(Long placeReviewId, User user) {
        PlaceReview placeReview = placeReviewRepository.findById(placeReviewId).orElseThrow(PlaceReviewNotFoundException::new);
        Optional<PlaceReviewLike> optionalPlaceReviewLike = placeReviewLikeRepository.findByPlaceReviewIdAndCreatedBy(placeReviewId, user);
        if (optionalPlaceReviewLike.isEmpty()) {
            placeReviewLikeRepository.save(PlaceReviewLike.create(placeReview));
        }
        if (optionalPlaceReviewLike.isPresent()) {
            PlaceReviewLike placeReviewLike = optionalPlaceReviewLike.get();
            placeReviewLikeRepository.delete(placeReviewLike);
        }
    }

    public void checkReviewUpdatable(Long placeReviewId, User user) {
        PlaceReview review = placeReviewRepository.findById(placeReviewId).orElseThrow(PlaceReviewNotFoundException::new);

        if (!review.getCreatedBy().getId().equals(user.getId())) {
            throw new PlaceReviewNotUpdatableException();
        }
    }
}
