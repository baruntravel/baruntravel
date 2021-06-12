package me.travelplan.service.wishlist.repository;

import me.travelplan.service.user.domain.User;
import me.travelplan.service.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByCreatedBy(User user);
}
