package me.travelplan.service.cart.repository;

import me.travelplan.service.cart.domain.CartPlace;
import me.travelplan.service.user.domain.User;

import java.util.List;

public interface CartPlaceRepositoryCustom {
    Integer findMaxOrderByCreatedBy(User user);

    List<CartPlace> findAllByCreatedBy(User user);
}
