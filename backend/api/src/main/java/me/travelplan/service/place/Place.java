package me.travelplan.service.place;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.file.File;

import javax.persistence.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places")
public class Place extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "image", referencedColumnName = "id")
    private File image;

    private Long thirdId;
    private String name;
    private String url;
    private Double x;
    private Double y;
}
