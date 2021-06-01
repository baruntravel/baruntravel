package me.travelplan.service.place.domain;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places_reviews_likes")
public class PlaceReviewLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_review_id")
    private PlaceReview placeReview;

    public static PlaceReviewLike create(PlaceReview placeReview) {
        PlaceReviewLike placeReviewLike = PlaceReviewLike.builder().placeReview(placeReview).build();
        placeReview.getPlaceReviewLikes().add(placeReviewLike);
        return placeReviewLike;
    }
}
