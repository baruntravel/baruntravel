package me.travelplan.service.place;


import lombok.*;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places_likes")
public class PlaceLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private void setPlace(Place place) {
        this.place = place;
        place.getPlaceLikes().add(this);
    }

    public static PlaceLike create(Place place) {
        PlaceLike placeLike = PlaceLike.builder().build();
        placeLike.setPlace(place);
        return placeLike;
    }
}

