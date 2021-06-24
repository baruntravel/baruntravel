package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.travelplan.component.kakaomap.KakaoMapPlace;
import me.travelplan.component.kakaomap.KakaoMapService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.repository.PlaceCategoryRepository;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.web.place.PlaceDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceCategoryRepository placeCategoryRepository;
    private final KakaoMapService kakaoMapService;

    public Place create(PlaceDto.Request placeDto) {
        Place place = Place.create(placeDto, placeCategoryRepository.getOne(placeDto.getCategoryId()));
        Place savedPlace = placeRepository.save(place);
        this.updateDetail(savedPlace.getId());
        return savedPlace;
    }

    @Transactional(readOnly = true)
    public Place getById(Long placeId) {
        return placeRepository.findByIdWithCategory(placeId).orElseThrow(PlaceNotFoundException::new);
    }

    public Place getByIdWithCrawling(Place place) {
        if (place.getName() != null && placeRepository.findById(place.getId()).isEmpty()) {
            Place savedPlace = placeRepository.save(place);
            this.updateDetail(place.getId());
            return savedPlace;
        }

        return placeRepository.findByIdWithCategory(place.getId()).orElseThrow(PlaceNotFoundException::new);
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
