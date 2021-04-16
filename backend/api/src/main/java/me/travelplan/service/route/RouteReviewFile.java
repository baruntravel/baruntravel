package me.travelplan.service.route;

import lombok.*;
import me.travelplan.service.file.File;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "routes_reviews_files")
public class RouteReviewFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_review_id")
    private RouteReview routeReview;

    public void setRouteReview(RouteReview routeReview) {
        this.routeReview = routeReview;
    }

    public void addFile(File file) {
        this.file = file;
        file.getRouteReviewFiles().add(this);
    }

    public static RouteReviewFile create(File file) {
        RouteReviewFile routeReviewFile = RouteReviewFile.builder().build();
        routeReviewFile.addFile(file);

        return routeReviewFile;
    }
}
