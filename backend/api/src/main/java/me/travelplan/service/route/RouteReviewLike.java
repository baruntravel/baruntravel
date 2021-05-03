package me.travelplan.service.route;

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

    public void setRouteReview(RouteReview routeReview) {
        this.routeReview = routeReview;
        routeReview.getRouteReviewLikes().add(this);
    }

    public static RouteReviewLike create(RouteReview routeReview) {
        RouteReviewLike routeReviewLike = RouteReviewLike.builder().build();
        routeReviewLike.setRouteReview(routeReview);
        return routeReviewLike;
    }
}
