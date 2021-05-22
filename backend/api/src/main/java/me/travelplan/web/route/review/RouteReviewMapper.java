package me.travelplan.web.route.review;

import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.route.domain.RouteReview;
import me.travelplan.service.route.domain.RouteReviewFile;
import me.travelplan.web.common.FileDto;
import me.travelplan.web.common.UserDto;
import me.travelplan.web.route.review.dto.RouteReviewDto;
import me.travelplan.web.route.review.dto.RouteReviewResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RouteReviewMapper {
    RouteReviewResponse.GetOnlyId toReviewIdResponse(RouteReview review);

    default RouteReviewResponse.GetList entityToResponseReviewList(List<RouteReview> reviews, CustomUserDetails customUserDetails) {
        return RouteReviewResponse.GetList.builder()
                .reviews(reviews.stream().map(routeReview -> {
                    UserDto.Response.ResponseBuilder userDtoBuilder = UserDto.Response.builder()
                            .email(routeReview.getCreatedBy().getEmail())
                            .name(routeReview.getCreatedBy().getName());
                    if (routeReview.getCreatedBy().getAvatar() != null) {
                        userDtoBuilder.avatarUrl(routeReview.getCreatedBy().getAvatar().getUrl());
                    }
                    return RouteReviewDto.Response.builder()
                            .id(routeReview.getId())
                            .content(routeReview.getContent())
                            .score(routeReview.getScore())
                            .isLike(routeReview.isLike(customUserDetails))
                            .likes(routeReview.getRouteReviewLikes().size())
                            .creator(userDtoBuilder.build())
                            .images(routeReview.getRouteReviewFiles().stream()
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
