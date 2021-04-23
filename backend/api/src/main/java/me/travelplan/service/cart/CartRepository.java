package me.travelplan.service.cart;

import me.travelplan.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCreatedBy(User user);
}
