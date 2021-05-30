package me.travelplan.web.place.review;

import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.place.domain.PlaceReviewImage;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.common.FileDto;
import me.travelplan.web.common.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PlaceReviewMapper {
    default List<PlaceReviewDto.Response> entityToResponseDto(List<PlaceReview> reviews, User currentUser) {
        return reviews.stream().map(review -> this.entityToResponseDto(review, currentUser)).collect(Collectors.toList());
    }

    default PlaceReviewDto.Response entityToResponseDto(PlaceReview review, User currentUser) {
        return PlaceReviewDto.Response.builder()
                .id(review.getId())
                .content(review.getContent())
                .score(review.getScore())
                .images(review.getImages().stream().map(PlaceReviewImage::getFile).map(File::getUrl).map(FileDto.Image::new).collect(Collectors.toList()))
                .createdBy(UserDto.Response.from(review.getCreatedBy()))
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .mine(review.getCreatedBy().getId().equals(currentUser.getId()))
                .build();
    }
}
