package me.travelplan.service.user.repository;

import me.travelplan.service.user.domain.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByEmailWithAvatar(String email);
}
