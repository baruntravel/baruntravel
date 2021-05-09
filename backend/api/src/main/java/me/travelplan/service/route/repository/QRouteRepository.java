package me.travelplan.service.route.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.travelplan.service.route.domain.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static me.travelplan.service.route.domain.QRoute.route;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QRouteRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Route> findAllByCoordinate(Double maxX, Double minX, Double maxY, Double minY, Pageable pageable) {
        QueryResults<Route> results = queryFactory.selectFrom(route)
                .where(route.minX.goe(minX)
                        .and(route.maxX.loe(maxX))
                        .and(route.minY.goe(minY))
                        .and(route.maxY.loe(maxY)))
                .join(route.createdBy).fetchJoin()
                .orderBy(route.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Route> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
