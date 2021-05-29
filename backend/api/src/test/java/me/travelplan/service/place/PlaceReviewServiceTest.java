package me.travelplan.service.place;

import me.travelplan.service.file.FileService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.place.exception.PlaceReviewNotUpdatableException;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.place.repository.PlaceReviewRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.place.review.PlaceReviewDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlaceReviewServiceTest {
    @InjectMocks
    private PlaceReviewService placeReviewService;

    @Mock private PlaceRepository placeRepository;
    @Mock private PlaceReviewRepository placeReviewRepository;
    @Mock private FileService fileService;

    @Test
    @DisplayName("장소 리뷰 생성 테스트")
    private void createReview() {
        given(placeRepository.findById(any())).willReturn(Optional.of(Place.builder().build()));
        PlaceReviewDto.Request request = PlaceReviewDto.Request.builder()
                .content("Content")
                .score(4.5)
                .images(Collections.emptyList())
                .build();

        placeReviewService.createReview(1L, request);
        verify(placeRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("장소 리뷰 삭제 테스트")
    public void deleteReview() {
        placeReviewService.deleteReview(1L);
        verify(placeReviewRepository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("장소 리뷰 수정 테스트")
    public void updateReview() {
        given(placeReviewRepository.findById(any())).willReturn(Optional.of(PlaceReview.builder().build()));
        PlaceReviewDto.Request request = PlaceReviewDto.Request.builder()
                .content("Content")
                .score(4.5)
                .images(Collections.emptyList())
                .build();

        placeReviewService.updateReview(1L, request);

        verify(placeReviewRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("장소 리뷰 수정/삭제 권한 테스트 : 수정 가능")
    public void checkReviewIsUpdatable() {
        User user = User.builder().id(1L).build();
        PlaceReview review = PlaceReview.builder().build();
        review.setCreatedBy(user);
        given(placeReviewRepository.findById(any())).willReturn(Optional.of(review));
        placeReviewService.checkReviewUpdatable(1L, user);
    }

    @Test
    @DisplayName("장소 리뷰 수정/삭제 권한 테스트 : 수정 불가능")
    public void checkReviewIsNotUpdatable() {
        User user = User.builder().id(1L).build();
        User createdBy = User.builder().id(2L).build();
        PlaceReview review = PlaceReview.builder().build();
        review.setCreatedBy(createdBy);
        given(placeReviewRepository.findById(any())).willReturn(Optional.of(review));
        assertThrows(PlaceReviewNotUpdatableException.class, () -> placeReviewService.checkReviewUpdatable(1L, user));
    }
}
