package me.travelplan.service.cart;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.cart.exception.PlaceExistedException;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceRepository;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.user.User;
import me.travelplan.web.cart.CartRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final PlaceRepository placeRepository;
    private final CartPlaceRepository cartPlaceRepository;

    @Transactional
    public void addPlace(CartRequest.AddPlace request, User user) {
        Cart cart = cartRepository.findByCreatedBy(user);
        if(cartPlaceRepository.findByPlaceId(request.getPlaceId()).isPresent()){
            throw new PlaceExistedException();
        }
        Place place = placeRepository.findById(request.getPlaceId()).orElseThrow(PlaceNotFoundException::new);
        CartPlace cartPlace = CartPlace.createWithCart(place, cart);
        cartPlaceRepository.save(cartPlace);
    }

    public Cart getMyCart(User user) {
        return cartRepository.findByCreatedBy(user);
    }
}
