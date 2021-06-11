package me.travelplan.service.wishlist.repository;

import me.travelplan.service.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
