package me.travelplan.service.wishlist.repository;

import me.travelplan.service.wishlist.domain.WishlistPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistPlaceRepository extends JpaRepository<WishlistPlace, Long> {
    Optional<WishlistPlace> findByWishlistIdAndPlaceId(Long wishlistId, Long id);
}
