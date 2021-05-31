package me.travelplan.service.route.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.travelplan.service.route.domain.RouteReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static me.travelplan.service.route.domain.QRouteReview.routeReview;
import static me.travelplan.service.user.domain.QUser.user;

@RequiredArgsConstructor
public class RouteReviewRepositoryImpl implements RouteReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<RouteReview> findAllByRouteId(Long routeId, String sortType, Pageable pageable) {
        JPAQuery<RouteReview> query = queryFactory.selectFrom(routeReview)
                .join(routeReview.createdBy, user).fetchJoin()
                .where(routeReview.route.id.eq(routeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        if (sortType.equals("best")) {
            query.orderBy(routeReview.routeReviewLikes.size().desc());
        } else query.orderBy(routeReview.id.desc());

        QueryResults<RouteReview> results = query.fetchResults();

        List<RouteReview> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
