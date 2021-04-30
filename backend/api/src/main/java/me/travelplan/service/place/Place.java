package me.travelplan.service.place;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.file.File;
import me.travelplan.service.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "places")
public class Place extends BaseEntity {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "image", referencedColumnName = "id")
    private File image;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "category", referencedColumnName = "id")
    private PlaceCategory category;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private List<PlaceReview> reviews;

    @OneToMany(mappedBy = "place")
    @Builder.Default
    private List<PlaceLike> placeLikes = new ArrayList<>();

    private String name;
    private String url;

    private Double x;
    private Double y;

    public boolean isLike(User user) {
        return this.placeLikes.stream().anyMatch(placeLike -> placeLike.getCreatedBy().getId().equals(user.getId()));
    }
}
