package me.travelplan.service.route;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.Place;
import me.travelplan.service.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "routes")
public class Route extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "min_x")
    private Double minX;
    @Column(name = "min_y")
    private Double minY;
    @Column(name = "max_x")
    private Double maxX;
    @Column(name = "max_y")
    private Double maxY;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private final List<RoutePlace> routePlaces = new ArrayList<>();

    @OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE)
    private final List<RouteReview> routeReviews = new ArrayList<>();

    @OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE)
    private final List<RouteLike> routeLikes = new ArrayList<>();

    public void addPlace(RoutePlace place) {
        this.routePlaces.add(place);
        place.setRoute(this);
    }

    public void calculateCoordinate(List<RoutePlace> routePlaces) {
        List<Place> places = routePlaces.stream().map(RoutePlace::getPlace).collect(Collectors.toList());

        this.minX = places.stream().mapToDouble(Place::getX).min().getAsDouble();
        this.minY = places.stream().mapToDouble(Place::getY).min().getAsDouble();
        this.maxX = places.stream().mapToDouble(Place::getX).max().getAsDouble();
        this.maxY = places.stream().mapToDouble(Place::getY).max().getAsDouble();
    }

    public HashMap<String, Double> calculateCenterCoordinate() {
        HashMap<String, Double> centerMap = new HashMap<>();
        centerMap.put("centerX", (this.maxX + this.minX) / 2);
        centerMap.put("centerY", (this.maxY + this.minY) / 2);
        return centerMap;
    }

    public boolean isLike(User user) {
        return this.routeLikes.stream().anyMatch(routeLike -> routeLike.getCreatedBy().getId().equals(user.getId()));
    }

    public static Route create(String name, List<RoutePlace> routePlaces) {
        Route route = Route.builder()
                .name(name)
                .build();
        routePlaces.forEach(route::addPlace);
        return route;
    }
}
