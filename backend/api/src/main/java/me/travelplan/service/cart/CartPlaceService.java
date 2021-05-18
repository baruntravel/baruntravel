package me.travelplan.service.cart;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.cart.domain.CartPlace;
import me.travelplan.service.cart.exception.CartPlaceNotFoundException;
import me.travelplan.service.cart.exception.PlaceExistedException;
import me.travelplan.service.cart.repository.CartPlaceRepository;
import me.travelplan.service.place.PlaceService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.cart.CartPlaceRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartPlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceService placeService;
    private final CartPlaceRepository cartPlaceRepository;

    public void addPlace(Place place, User user) {
        if (placeRepository.findById(place.getId()).isEmpty()) {
            placeRepository.save(place);
            placeService.updateDetail(place.getId());
        }
        if (cartPlaceRepository.findByPlaceIdAndCreatedBy(place.getId(), user).isPresent()) {
            throw new PlaceExistedException();
        }
        CartPlace cartPlace = CartPlace.create(place, cartPlaceRepository.findMaxOrderByCreatedBy(user));
        cartPlaceRepository.save(cartPlace);
    }

    @Transactional(readOnly = true)
    public List<CartPlace> getMyCart(User user) {
        return cartPlaceRepository.findAllByCreatedBy(user);
    }

    public void addMemo(Long placeId, CartPlaceRequest.AddMemo request, User user) {
        CartPlace cartPlace = cartPlaceRepository.findByPlaceIdAndCreatedBy(placeId, user).orElseThrow(CartPlaceNotFoundException::new);
        cartPlace.addMemo(request.getMemo());
    }

    public void updatePlaceOrder(CartPlaceRequest.UpdateOrder request, User user) {
        CartPlace firstCartPlace = cartPlaceRepository.findByPlaceIdAndCreatedBy(request.getFirstPlaceId(), user).orElseThrow(CartPlaceNotFoundException::new);
        CartPlace secondCartPlace = cartPlaceRepository.findByPlaceIdAndCreatedBy(request.getSecondPlaceId(), user).orElseThrow(CartPlaceNotFoundException::new);
        CartPlace.swapOrder(firstCartPlace, secondCartPlace);
    }

    public void deleteOnePlace(Long placeId, User user) {
        cartPlaceRepository.deleteByPlaceIdAndCreatedBy(placeId, user);

        List<CartPlace> cartPlace = cartPlaceRepository.findAllByCreatedBy(user);
        CartPlace.updateAllOrder(cartPlace);
    }

    public void deleteAllPlace(User user) {
        cartPlaceRepository.deleteAllByCreatedBy(user);
    }
}
