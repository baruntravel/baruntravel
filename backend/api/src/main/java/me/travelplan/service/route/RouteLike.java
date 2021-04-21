package me.travelplan.service.route;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "routes_likes")
public class RouteLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    private void setRoute(Route route) {
        this.route = route;
        route.getRouteLikes().add(this);
    }

    public static RouteLike create(Route route) {
        RouteLike routeLike = RouteLike.builder().build();
        routeLike.setRoute(route);
        return  routeLike;
    }
}
