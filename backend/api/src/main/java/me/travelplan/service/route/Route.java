package me.travelplan.service.route;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.Place;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "routes")
public class Route extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "route", cascade = CascadeType.PERSIST)
    private final List<RoutePlace> places = new ArrayList<>();

    private String name;
    private Double x;
    private Double y;

    public void addPlace(RoutePlace place) {
        this.places.add(place);
        place.setRoute(this);
    }
}
