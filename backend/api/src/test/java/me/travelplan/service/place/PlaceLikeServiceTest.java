package me.travelplan.service.place;

import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceLike;
import me.travelplan.service.place.repository.PlaceLikeRepository;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlaceLikeServiceTest {
    @InjectMocks
    private PlaceLikeService placeLikeService;

    @Mock private PlaceRepository placeRepository;
    @Mock private PlaceLikeRepository placeLikeRepository;

    @Test
    @DisplayName("장소 좋아요 테스트")
    public void createLikeTest() {
        given(placeRepository.findById(any())).willReturn(Optional.of(Place.builder().build()));
        given(placeLikeRepository.findByPlaceIdAndCreatedBy(any(), any())).willReturn(Optional.empty());
        placeLikeService.createOrDeleteLike(1L, User.builder().build());
        verify(placeLikeRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("장소 좋아요 취소 테스트 (이미 좋아요를 한 상태)")
    public void deleteLikeTest() {
        given(placeRepository.findById(any())).willReturn(Optional.of(Place.builder().build()));
        given(placeLikeRepository.findByPlaceIdAndCreatedBy(any(), any())).willReturn(Optional.of(PlaceLike.builder().build()));
        placeLikeService.createOrDeleteLike(1L, User.builder().build());
        verify(placeLikeRepository, times(1)).delete(any());
    }
}
