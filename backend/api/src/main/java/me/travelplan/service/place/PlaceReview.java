package me.travelplan.service.place;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places_reviews")
public class PlaceReview extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private Double score;
    private String content;

    public void setPlace(Place place) {
        this.place = place;
    }

    public void update(PlaceReview changed) {
        this.score = changed.getScore();
        this.content = changed.getContent();
    }
}
