package me.travelplan.service.wishlist;

import lombok.RequiredArgsConstructor;
import me.travelplan.exception.PermissionDeniedException;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.service.wishlist.domain.Wishlist;
import me.travelplan.service.wishlist.domain.WishlistPlace;
import me.travelplan.service.wishlist.exception.WishlistNotFoundException;
import me.travelplan.service.wishlist.exception.WishlistPlaceDuplicatedException;
import me.travelplan.service.wishlist.repository.WishlistPlaceRepository;
import me.travelplan.service.wishlist.repository.WishlistRepository;
import me.travelplan.web.wishlist.dto.WishlistRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final PlaceRepository placeRepository;
    private final WishlistPlaceRepository wishlistPlaceRepository;

    public Wishlist create(WishlistRequest.Create request) {
        Wishlist wishlist = Wishlist.create(request.getName());
        return wishlistRepository.save(wishlist);
    }

    public void addPlace(Long wishlistId, WishlistRequest.AddPlace request, User user) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(WishlistNotFoundException::new);
        permissionCheck(user, wishlist);
        Place place = placeRepository.findById(request.getPlaceId()).orElseThrow(PlaceNotFoundException::new);
        if (wishlistPlaceRepository.findByWishlistIdAndPlaceId(wishlistId, place.getId()).isPresent()) {
            throw new WishlistPlaceDuplicatedException();
        }
        WishlistPlace wishlistPlace = WishlistPlace.create(wishlist, place);

        wishlistPlaceRepository.save(wishlistPlace);
    }

    @Transactional(readOnly = true)
    public List<Wishlist> getMine(User user) {
        return wishlistRepository.findAllByCreatedBy(user);
    }

    @Transactional(readOnly = true)
    public List<WishlistPlace> getPlaces(Long wishlistId) {
        return wishlistPlaceRepository.findByWishlistId(wishlistId);
    }

    public void delete(Long wishlistId, User user) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(WishlistNotFoundException::new);
        permissionCheck(user, wishlist);
        wishlistRepository.deleteById(wishlistId);
    }

    private void permissionCheck(User user, Wishlist wishlist) {
        if (!wishlist.getCreatedBy().getId().equals(user.getId())) {
            throw new PermissionDeniedException();
        }
    }
}
