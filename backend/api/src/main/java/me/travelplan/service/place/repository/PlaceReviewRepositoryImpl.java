package me.travelplan.service.place.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.travelplan.service.place.domain.PlaceReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static me.travelplan.service.place.domain.QPlaceReview.placeReview;

@RequiredArgsConstructor
public class PlaceReviewRepositoryImpl implements PlaceReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PlaceReview> findAllByPlaceId(Long placeId,String sortType, Pageable pageable) {
        JPAQuery<PlaceReview> query = queryFactory.selectFrom(placeReview)
                .join(placeReview.createdBy).fetchJoin()
                .where(placeReview.place.id.eq(placeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        if (sortType.equals("best")) {
            query.orderBy(placeReview.placeReviewLikes.size().desc());
        } else query.orderBy(placeReview.id.desc());

        QueryResults<PlaceReview> results = query.fetchResults();

        List<PlaceReview> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
