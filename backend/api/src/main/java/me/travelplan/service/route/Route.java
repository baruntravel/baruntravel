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

    private String name;

    private Point minPoint;
    private Point maxPoint;

    public void addPlace(RoutePlace place) {
        this.places.add(place);
        place.setRoute(this);
    }

    public void calculateCoordinate(List<RoutePlace> routePlaces) {
        List<Place> places = routePlaces.stream().map(RoutePlace::getPlace).collect(Collectors.toList());

        DoubleStream xList = places.stream().map(Place::getPoint).mapToDouble(Point::getX);
        DoubleStream yList = places.stream().map(Place::getPoint).mapToDouble(Point::getY);

        this.minPoint = (new GeometryFactory()).createPoint(new Coordinate(
                xList.min().orElseThrow(),
                yList.min().orElseThrow()
        ));

        this.maxPoint = (new GeometryFactory()).createPoint(new Coordinate(
                xList.max().orElseThrow(),
                yList.max().orElseThrow()
        ));
    }
}
