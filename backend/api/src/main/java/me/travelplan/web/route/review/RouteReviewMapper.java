package me.travelplan.web.route.review;

import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.route.domain.RouteReview;
import me.travelplan.service.route.domain.RouteReviewFile;
import me.travelplan.web.common.FileDto;
import me.travelplan.web.common.UserDto;
import me.travelplan.web.route.review.dto.RouteReviewResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RouteReviewMapper {
    RouteReviewResponse.GetOnlyId toReviewIdResponse(RouteReview review);

    default List<RouteReviewResponse.GetList> entityToResponseReviewList(List<RouteReview> reviews, CustomUserDetails customUserDetails) {
        List<RouteReviewResponse.GetList> list = new ArrayList<>();
        reviews.forEach(review -> {
            RouteReviewResponse.GetList getList = RouteReviewResponse.GetList.builder()
                    .id(review.getId())
                    .content(review.getContent())
                    .score(review.getScore())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .isLike(review.isLike(customUserDetails))
                    .likes(review.getRouteReviewLikes().size())
                    .images(review.getRouteReviewFiles().stream()
                            .map(RouteReviewFile::getFile)
                            .map(File::getUrl)
                            .map(FileDto.Image::new)
                            .collect(Collectors.toList()))
                    .creator(UserDto.Response.from(review.getCreatedBy()))
                    .build();
            list.add(getList);
        });
        return list;
    }
}
