package me.travelplan.web.route.review;

import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.route.domain.RouteReview;
import me.travelplan.service.route.domain.RouteReviewFile;
import me.travelplan.web.common.FileDto;
import me.travelplan.web.route.RouteDto;
import me.travelplan.web.route.RouteResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RouteReviewMapper {
    RouteResponse.ReviewId toReviewIdResponse(RouteReview review);

    default RouteResponse.ReviewList entityToResponseReviewList(List<RouteReview> reviews, CustomUserDetails customUserDetails) {
        return RouteResponse.ReviewList.builder()
                .reviews(reviews.stream().map(routeReview -> {
                    RouteDto.Creator.CreatorBuilder creatorBuilder = RouteDto.Creator.builder()
                            .name(routeReview.getCreatedBy().getName());
                    if (routeReview.getCreatedBy().getAvatar() != null) {
                        creatorBuilder.avatar(routeReview.getCreatedBy().getAvatar().getUrl());
                    }
                    return RouteDto.ReviewResponse.builder()
                            .id(routeReview.getId())
                            .content(routeReview.getContent())
                            .score(routeReview.getScore())
                            .likeCheck(routeReview.isLike(customUserDetails))
                            .likeCount(routeReview.getRouteReviewLikes().size())
                            .creator(creatorBuilder.build())
                            .files(routeReview.getRouteReviewFiles().stream()
                                    .map(RouteReviewFile::getFile)
                                    .map(File::getUrl)
                                    .map(FileDto.Image::new)
                                    .collect(Collectors.toList()))
                            .createdAt(routeReview.getCreatedAt())
                            .updatedAt(routeReview.getUpdatedAt())
                            .build();
                }).collect(Collectors.toList()))
                .build();
    }
}
