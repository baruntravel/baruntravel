package me.travelplan.service.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceLike;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.repository.PlaceLikeRepository;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceLikeService {
    private final PlaceRepository placeRepository;
    private final PlaceLikeRepository placeLikeRepository;

    public void createOrDeleteLike(Long placeId, User user) {
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
}
