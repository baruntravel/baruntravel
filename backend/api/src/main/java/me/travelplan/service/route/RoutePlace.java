package me.travelplan.service.route;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import me.travelplan.service.place.Place;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @Column(name = "`order`")
    private Integer order;

    public void setRoute(Route route) {
        this.route = route;
    }
}
