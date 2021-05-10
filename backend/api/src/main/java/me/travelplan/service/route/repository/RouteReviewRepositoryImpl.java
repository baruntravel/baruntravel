package me.travelplan.service.route.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.travelplan.service.route.domain.RouteReview;

import java.util.List;

import static me.travelplan.service.route.domain.QRouteReview.routeReview;
import static me.travelplan.service.user.domain.QUser.user;

@RequiredArgsConstructor
public class RouteReviewRepositoryImpl implements RouteReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RouteReview> findAllByRouteId(Long routeId) {
        return queryFactory.selectFrom(routeReview)
                .join(routeReview.createdBy, user).fetchJoin()
                .where(routeReview.route.id.eq(routeId))
                .fetch();
    }
}
