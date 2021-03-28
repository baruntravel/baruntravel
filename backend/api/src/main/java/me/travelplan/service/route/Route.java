package me.travelplan.service.route;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.Place;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "routes")
public class Route extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private final List<RoutePlace> places = new ArrayList<>();

    private String name;
    private Double x;
    private Double y;

    public void addPlace(RoutePlace place) {
        this.places.add(place);
        place.setRoute(this);
    }

    public void calculateCenterCoordinate() {
        if (!this.places.isEmpty()) {
            this.x = this.places.stream().map(RoutePlace::getPlace).mapToDouble(Place::getX).average().orElseThrow();
            this.y = this.places.stream().map(RoutePlace::getPlace).mapToDouble(Place::getY).average().orElseThrow();
        }
    }
}
