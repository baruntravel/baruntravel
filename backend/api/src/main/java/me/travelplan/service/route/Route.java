package me.travelplan.service.route;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.Place;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

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

    @OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE)
    private final List<RouteReview> routeReviews = new ArrayList<>();

    private String name;

    @Column(name = "min_x")
    private Double minX;
    @Column(name = "min_y")
    private Double minY;
    @Column(name = "max_x")
    private Double maxX;
    @Column(name = "max_y")
    private Double maxY;

    public void addPlace(RoutePlace place) {
        this.places.add(place);
        place.setRoute(this);
    }

    public void addReview(RouteReview routeReview){
        this.routeReviews.add(routeReview);
        routeReview.setRoute(this);
    }

    public void calculateCoordinate(List<RoutePlace> routePlaces) {
        List<Place> places = routePlaces.stream().map(RoutePlace::getPlace).collect(Collectors.toList());

        this.minX = places.stream().mapToDouble(Place::getX).min().getAsDouble();
        this.minY = places.stream().mapToDouble(Place::getY).min().getAsDouble();
        this.maxX = places.stream().mapToDouble(Place::getX).max().getAsDouble();
        this.maxY = places.stream().mapToDouble(Place::getY).max().getAsDouble();
    }
}
