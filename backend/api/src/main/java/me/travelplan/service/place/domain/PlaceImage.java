package me.travelplan.service.place.domain;

import lombok.*;
import me.travelplan.service.file.domain.File;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places_images")
public class PlaceImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "file_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private File file;

    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;
}
