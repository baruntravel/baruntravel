package me.travelplan.service.place.domain;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.web.place.review.PlaceReviewDto;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE places_reviews SET deleted_at=CURRENT_TIMESTAMP WHERE `id`=?")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places_reviews")
public class PlaceReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private Double score;
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PlaceReviewImage> images = new ArrayList<>();

    public void setPlace(Place place) {
        this.place = place;
    }

    public void update(PlaceReviewDto.Request changed, List<PlaceReviewImage> images) {
        this.score = changed.getScore();
        this.content = changed.getContent();
        images.forEach(this::addImage);
    }

    public void addImage(PlaceReviewImage image) {
        this.images.add(image);
        image.setPlaceReview(this);
    }

    public static PlaceReview from(PlaceReviewDto.Request reviewDto, Place place, List<PlaceReviewImage> images) {
        PlaceReview review = PlaceReview.builder()
                .place(place)
                .content(reviewDto.getContent())
                .score(reviewDto.getScore())
                .build();
        images.forEach(review::addImage);
        return review;
    }
}
