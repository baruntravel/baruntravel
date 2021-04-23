package me.travelplan.service.cart;

import me.travelplan.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCreatedBy(User user);
}
