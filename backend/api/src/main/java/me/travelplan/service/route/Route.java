package me.travelplan.service.route;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "routes")
public class Route extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "route")
    private List<RoutePlace> places;

    private String name;
    private Double x;
    private Double y;
}
