package me.travelplan.web.wishlist.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.travelplan.web.common.FileDto;

import java.util.List;

public class WishlistResponse {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetOnlyId {
        private final Long id;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetOnlyWishlistPlaceId {
        private final Long id;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetMine {
        private final Long id;
        private final String name;
        private final List<FileDto.Image> images;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetPlaces {
        private final List<WishlistDto.Place> places;
    }
}
