package me.travelplan.service.route.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.travelplan.service.route.domain.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static me.travelplan.service.route.domain.QRoute.route;

@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<Route> findAllByCoordinate(Double maxX, Double minX, Double maxY, Double minY, String sortType, Pageable pageable) {
        JPAQuery<Route> query = queryFactory.selectFrom(route)
                .where(route.minX.goe(minX)
                        .and(route.maxX.loe(maxX))
                        .and(route.minY.goe(minY))
                        .and(route.maxY.loe(maxY)))
                .join(route.createdBy).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (sortType.equals("best")) {
            query.orderBy(route.routeLikes.size().desc());
        } else query.orderBy(route.id.desc());

        QueryResults<Route> results = query.fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Route> findAllByRegion(String region, String sortType, Pageable pageable) {
        JPAQuery<Route> query = queryFactory.selectFrom(route)
                .where(route.region.eq(region))
                .join(route.createdBy).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (sortType.equals("best")) {
            query.orderBy(route.routeLikes.size().desc());
        } else query.orderBy(route.id.desc());

        QueryResults<Route> results = query.fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Optional<Route> findByIdWithUser(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(route)
                .join(route.createdBy).fetchJoin()
                .where(route.id.eq(id))
                .fetchOne());
    }
}
