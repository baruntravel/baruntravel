package me.travelplan.service.cart.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.travelplan.service.user.domain.User;

import static me.travelplan.service.cart.domain.QCartPlace.cartPlace;

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
}
