package me.travelplan.service.route;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.Place;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "`point`")
    private Point point;

    public void addPlace(RoutePlace place) {
        this.places.add(place);
        place.setRoute(this);
    }

    public void calculateCenterCoordinate() {
        if (!this.places.isEmpty()) {
            this.point = (new GeometryFactory()).createPoint(new Coordinate(
                this.places.stream().map(RoutePlace::getPlace).map(Place::getPoint).mapToDouble(Point::getX).average().orElseThrow(),
                this.places.stream().map(RoutePlace::getPlace).map(Place::getPoint).mapToDouble(Point::getY).average().orElseThrow()
            ));
        }
    }
}
