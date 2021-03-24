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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @Column(name = "`order`")
    private Integer order;

    public void setRoute(Route route) {
        this.route = route;
    }
}
