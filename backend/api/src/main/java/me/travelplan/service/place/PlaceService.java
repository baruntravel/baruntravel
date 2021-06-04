package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.travelplan.component.kakaomap.KakaoMapPlace;
import me.travelplan.component.kakaomap.KakaoMapService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceDetailStatus;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.repository.PlaceCategoryRepository;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.web.place.PlaceDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceCategoryRepository placeCategoryRepository;
    private final KakaoMapService kakaoMapService;

    public Place getById(Long placeId) {
        return placeRepository.findByIdWithCategory(placeId).orElseThrow(PlaceNotFoundException::new);
    }

    @Transactional
    public Place create(PlaceDto.Request placeDto) {
        Place place = Place.builder()
                .id(placeDto.getId())
                .category(placeCategoryRepository.getOne(placeDto.getCategoryId()))
                .name(placeDto.getName())
                .url(placeDto.getUrl())
                .address(placeDto.getAddress())
                .x(placeDto.getX())
                .y(placeDto.getY())
                .detailStatus(PlaceDetailStatus.PENDING)
                .build();
        Place savedPlace = placeRepository.save(place);
        this.updateDetail(savedPlace.getId());
        return savedPlace;
    }

    //    @Async
    public void updateDetail(Long id) {
        try {
            Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
            KakaoMapPlace kakaoPlace = kakaoMapService.getKakaoMapPlace(id).orElseThrow();
            place.setFromKakaoMapPlace(kakaoPlace);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
