package me.travelplan.service.route.domain;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.place.domain.Place;

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
    private String region;
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

    public boolean isLike(CustomUserDetails customUserDetails) {
        return customUserDetails != null && this.routeLikes.stream().anyMatch(routeLike -> routeLike.getCreatedBy().getId().equals(customUserDetails.getUser().getId()));
    }

    public Double getAverageReviewScore() {
        return this.routeReviews.stream().mapToDouble(RouteReview::getScore).average().orElse(0);
    }

    public static Route create(String name, String region, List<RoutePlace> routePlaces) {
        Route route = Route.builder()
                .name(name)
                .region(region)
                .build();
        routePlaces.forEach(route::addPlace);
        return route;
    }
}
