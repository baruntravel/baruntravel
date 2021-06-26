package me.travelplan.service.place.domain;

import lombok.*;
import me.travelplan.component.kakaomap.KakaoMapPlace;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.file.domain.File;
import me.travelplan.web.place.PlaceDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final List<PlaceReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    @Builder.Default
    private final List<PlaceLike> placeLikes = new ArrayList<>();
    /**
     * Detail
     */
    @OneToMany(mappedBy = "place", cascade = CascadeType.PERSIST)
    private List<PlaceImage> images;
    private String openHour;

    public boolean isLike(CustomUserDetails customUserDetails) {
        return customUserDetails != null && this.placeLikes.stream().anyMatch(placeLike -> placeLike.getCreatedBy().getId().equals(customUserDetails.getUser().getId()));
    }

    public Place setFromKakaoMapPlace(KakaoMapPlace kakaoMapPlace) {
        try {
            this.detailStatus = PlaceDetailStatus.COMPLETE;
            this.thumbnail = File.createExternalImage(kakaoMapPlace.getThumbnail());
            this.images = kakaoMapPlace.getPhotos().stream()
                    .map(photo -> File.createExternalImage(photo.getImageUrl()))
                    .map(file -> PlaceImage.builder().place(this).file(file).build())
                    .collect(Collectors.toList());
            this.openHour = kakaoMapPlace.getOpenHour().get(0).getTimeList().get(0).getTimeSE();
        } catch (Exception ignored) {
            // ignore
        }

        return this;
    }

    public Double getAverageReviewScore() {
        return this.reviews.stream().mapToDouble(PlaceReview::getScore).average().orElse(0);
    }

    public static Place create(PlaceDto.Request request, PlaceCategory category) {
        return Place.builder()
                .id(request.getId())
                .category(category)
                .name(request.getName())
                .url(request.getUrl())
                .address(request.getAddress())
                .x(request.getX())
                .y(request.getY())
                .detailStatus(PlaceDetailStatus.PENDING)
                .build();
    }
}
