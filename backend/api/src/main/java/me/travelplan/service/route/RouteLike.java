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

    private boolean likeCheck;  //좋아요 체크 -> true 취소 -> false

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    public void updateLikeCheck(boolean likeCheck){
        this.likeCheck= !likeCheck;
    }

    public static RouteLike create(Route route) {
        return RouteLike.builder()
                .route(route)
                .likeCheck(true)
                .build();

    }

}
