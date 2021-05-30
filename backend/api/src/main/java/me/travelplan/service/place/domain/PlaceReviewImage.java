package me.travelplan.service.place.domain;

import lombok.*;
import me.travelplan.service.file.domain.File;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places_reviews_images")
public class PlaceReviewImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private PlaceReview review;

    public PlaceReviewImage setPlaceReview(PlaceReview review) {
        this.review = review;
        return this;
    }
}
