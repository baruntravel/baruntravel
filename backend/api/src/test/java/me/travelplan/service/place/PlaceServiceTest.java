package me.travelplan.service.place;

import me.travelplan.component.kakaomap.KakaoMapService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceCategory;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.repository.PlaceCategoryRepository;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.web.place.PlaceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceTest {
    @InjectMocks
    private PlaceService placeService;

    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private PlaceCategoryRepository placeCategoryRepository;
    @Mock
    private KakaoMapService kakaoMapService;

    @Test
    @DisplayName("장소 생성")
    public void create() {
        PlaceDto.Request request = PlaceDto.Request.builder()
                .id(123L)
                .name("Hello")
                .x(10.11)
                .y(11.23)
                .address("Address")
                .categoryId("123")
                .url("http://example.com")
                .build();

        given(placeCategoryRepository.getOne(any())).willReturn(PlaceCategory.builder().build());
        given(placeRepository.save(any())).willReturn(Place.builder().build());

        placeService.create(request);

        verify(placeCategoryRepository, times(1)).getOne(any());
        verify(placeRepository).save(any());
    }

    @Test
    @DisplayName("장소 하나 가져오기")
    public void getById() {
        given(placeRepository.findByIdWithCategory(any())).willReturn(Optional.of(Place.builder().id(1L).build()));
        Place place = placeService.getById(1L, Place.builder().build());

        verify(placeRepository, times(1)).findByIdWithCategory(any());
        assertEquals(place.getId(), 1L);
    }

    @Test
    @DisplayName("장소 하나 가져오기 : 장소가 없을경우")
    public void getByIdException() {
        given(placeRepository.findByIdWithCategory(any())).willReturn(Optional.empty());

        assertThrows(PlaceNotFoundException.class, () -> placeService.getById(1L, Place.builder().build()));
    }

    @Test
    @DisplayName("DB에 없는 장소 조회(크롤링 과정 포함)")
    public void getByIdWithCrawling() {
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        placeService.getById(1L, Place.builder().id(1234L).name("테스트 장소 이름").build());

        verify(placeRepository, times(1)).save(any());
    }
}
