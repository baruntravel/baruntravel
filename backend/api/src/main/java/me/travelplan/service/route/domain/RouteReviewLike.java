package me.travelplan.service.route.domain;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "routes_reviews_likes")
public class RouteReviewLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_review_id")
    private RouteReview routeReview;

    public static RouteReviewLike create(RouteReview routeReview) {
        RouteReviewLike routeReviewLike = RouteReviewLike.builder().routeReview(routeReview).build();
        routeReview.getRouteReviewLikes().add(routeReviewLike);
        return routeReviewLike;
    }
}
