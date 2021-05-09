package me.travelplan.service.place.domain;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.file.domain.File;
import me.travelplan.component.kakaomap.KakaoMapPlace;
import me.travelplan.service.user.domain.User;

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
    private String name;
    private String url;
    private Double x;
    private Double y;
    private String address;
    @Enumerated(EnumType.STRING)
    private PlaceDetailStatus detailStatus;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File thumbnail;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PlaceCategory category;

    @OneToMany(mappedBy = "place")
    @Builder.Default
    private List<PlaceReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    @Builder.Default
    private List<PlaceLike> placeLikes = new ArrayList<>();
    /**
     * Detail
     */
    @OneToMany(mappedBy = "place")
    private List<PlaceImage> images;
    private String openHour;

    public boolean isLike(User user) {
        return this.placeLikes.stream().anyMatch(placeLike -> placeLike.getCreatedBy().getId().equals(user.getId()));
    }

    public Place setFromKakaoMapPlace(KakaoMapPlace kakaoMapPlace) {
        this.detailStatus = PlaceDetailStatus.COMPLETE;
        this.thumbnail = File.createExternalImage(kakaoMapPlace.getThumbnail());
        try {
            this.openHour = kakaoMapPlace.getOpenHour().get(0).getTimeList().get(0).getTimeSE();
        } catch (Exception ex) {
            // ignore
        }

        return this;
    }

    public Double getReviewScoreAvg() {
        return this.reviews.stream().mapToDouble(PlaceReview::getScore).average().orElse(0);
    }
}
