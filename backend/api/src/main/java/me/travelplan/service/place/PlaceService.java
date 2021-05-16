package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.travelplan.component.kakaomap.KakaoMapPlace;
import me.travelplan.component.kakaomap.KakaoMapService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final KakaoMapService kakaoMapService;

    public Place getById(Long id) {
        return placeRepository.findByIdWithCategory(id).orElseThrow(PlaceNotFoundException::new);
    }

    @Transactional
    public Place create(Place place) {
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
