package me.travelplan.service.cart.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.travelplan.service.cart.domain.CartPlace;
import me.travelplan.service.user.domain.User;

import java.util.List;

import static me.travelplan.service.cart.domain.QCartPlace.cartPlace;
import static me.travelplan.service.place.domain.QPlace.place;

@RequiredArgsConstructor
public class CartPlaceRepositoryImpl implements CartPlaceRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Integer findMaxOrderByCreatedBy(User user) {
        Integer maxOrder = queryFactory.select(cartPlace.order.max())
                .from(cartPlace)
                .where(cartPlace.createdBy.eq(user))
                .fetchOne();
        if (maxOrder == null) {
            return 0;
        }
        return maxOrder;
    }

    @Override
    public List<CartPlace> findAllByCreatedBy(User user) {
        return queryFactory.selectFrom(cartPlace)
                .join(cartPlace.place, place).fetchJoin()
                .join(place.category).fetchJoin()
                .where(cartPlace.createdBy.eq(user))
                .orderBy(cartPlace.order.asc())
                .fetch();
    }
}
