package me.travelplan.service.wishlist;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.wishlist.domain.Wishlist;
import me.travelplan.service.wishlist.repository.WishlistRepository;
import me.travelplan.web.wishlist.dto.WishlistRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public Wishlist create(WishlistRequest.Create request) {
        Wishlist wishlist = Wishlist.create(request.getName());
        return wishlistRepository.save(wishlist);
    }
}
