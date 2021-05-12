package me.travelplan.service.cart;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.cart.domain.CartPlace;
import me.travelplan.service.cart.exception.CartPlaceNotFoundException;
import me.travelplan.service.cart.exception.PlaceDuplicatedException;
import me.travelplan.service.cart.repository.CartPlaceRepository;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.place.PlaceService;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.cart.CartPlaceRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartPlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceService placeService;
    private final CartPlaceRepository cartPlaceRepository;

    public List<CartPlace> getMyCart(User user) {
        return cartPlaceRepository.findAllByCreatedBy(user);
    }

    @Transactional
    public void addPlace(Place place, User user) {
        place = placeService.create(place);

        if (cartPlaceRepository.existsByPlaceIdAndCreatedBy(place.getId(), user)) {
            throw new PlaceDuplicatedException();
        }
        cartPlaceRepository.save(CartPlace.create(place));
    }

    @Transactional
    public void addMemo(Long placeId, CartPlaceRequest.AddMemo request, User user) {
        CartPlace cartPlace = cartPlaceRepository.findByPlaceIdAndCreatedBy(placeId, user).orElseThrow(CartPlaceNotFoundException::new);
        cartPlace.addMemo(request.getMemo());
    }

    @Transactional
    public void deleteOnePlace(Long placeId, User user) {
        cartPlaceRepository.deleteByPlaceIdAndCreatedBy(placeId, user);
    }

    @Transactional
    public void deleteAllPlace(User user) {
        cartPlaceRepository.deleteAllByCreatedBy(user);
    }
}
