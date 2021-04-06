package me.travelplan.service.route;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static me.travelplan.service.route.QRoute.route;

public class RouteRepositoryImpl implements RouteCustomRepository {
    private final JPAQueryFactory query;

    public RouteRepositoryImpl(JPAQueryFactory query) {
        this.query = query;
    }

    @Override
    public Page<Route> findAllByCoordinate(Double maxX, Double minX, Double maxY, Double minY, Pageable pageable) {
        QueryResults<Route> results = query.selectFrom(route)
                .where(route.minX.goe(minX)
                        .and(route.maxX.loe(maxX))
                        .and(route.minY.goe(minY))
                        .and(route.maxY.loe(maxY)))
                .orderBy(route.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Route> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
