package me.travelplan.service.route;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.Place;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    @Column(name = "max_x")
    private Double maxX;
    @Column(name = "min_y")
    private Double minY;
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

        Comparator<Place> sortX = new Comparator<Place>() {
            public int compare(Place o1, Place o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        };

        places.sort(sortX);

        this.minX=places.get(0).getX();
        this.maxX=places.get(places.size()-1).getX();
        Comparator<Place> sortY = new Comparator<Place>() {
            public int compare(Place o1, Place o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        };
        places.sort(sortY);

        this.minY=places.get(0).getY();
        this.maxY=places.get(places.size() - 1).getY();
    }
//    public void calculateCenterCoordinate() {
//        if (!this.places.isEmpty()) {
//            this.x = this.places.stream().map(RoutePlace::getPlace).mapToDouble(Place::getX).average().orElseThrow();
//            this.y = this.places.stream().map(RoutePlace::getPlace).mapToDouble(Place::getY).average().orElseThrow();
//        }
//    }
}
