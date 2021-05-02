package me.travelplan.service.route;

import lombok.*;
import me.travelplan.service.place.Place;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "routes_places")
public class RoutePlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "`order`")
    private Integer order;

    public void setRoute(Route route) {
        this.route = route;
    }

    public static RoutePlace create(Place place, Integer order) {
        return RoutePlace.builder()
                .place(place)
                .order(order)
                .build();
    }
}
