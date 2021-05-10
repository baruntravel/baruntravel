package me.travelplan.service.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.travelplan.service.user.domain.User;

import java.util.Optional;

import static me.travelplan.service.user.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByEmailWithAvatar(String email) {
        return Optional.ofNullable(queryFactory.selectFrom(user)
                .join(user.avatar).fetchJoin()
                .fetchOne());
    }
}
