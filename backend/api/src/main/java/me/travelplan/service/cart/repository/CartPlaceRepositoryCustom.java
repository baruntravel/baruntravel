package me.travelplan.service.cart.repository;

import me.travelplan.service.user.domain.User;

public interface CartPlaceRepositoryCustom {
    Integer findMaxOrderByCreatedBy(User user);
}
