package me.travelplan.service.place;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.file.File;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places")
public class Place extends BaseEntity {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "image", referencedColumnName = "id")
    private File image;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category", referencedColumnName = "id")
    private PlaceCategory category;

    private String name;
    private String url;

    private Double x;
    private Double y;
}
