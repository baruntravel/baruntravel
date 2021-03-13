package me.travelplan.service.route;

import lombok.NoArgsConstructor;
import me.travelplan.service.place.Place;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "routes_places")
public class RoutePlace {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    private Integer order;
}
