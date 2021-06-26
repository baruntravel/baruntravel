package me.travelplan.web.wishlist;

import me.travelplan.service.place.domain.Place;
import me.travelplan.service.wishlist.domain.Wishlist;
import me.travelplan.service.wishlist.domain.WishlistPlace;
import me.travelplan.web.common.FileDto;
import me.travelplan.web.wishlist.dto.WishlistDto;
import me.travelplan.web.wishlist.dto.WishlistRequest;
import me.travelplan.web.wishlist.dto.WishlistResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface WishlistMapper {
    WishlistResponse.GetOnlyId toWishlistId(Wishlist wishlist);

    @Mapping(constant = "PENDING", target = "detailStatus")
    @Mapping(source = "place.categoryId", target = "category.id")
    @Mapping(source = "place.categoryName", target = "category.name")
    @Mapping(constant = "", target = "openHour")
    Place dtoToPlace(WishlistRequest.AddKakaoPlace place);

    default List<WishlistResponse.GetMine> toGetMine(List<Wishlist> wishlists) {
        return wishlists.stream().map(wishlist -> WishlistResponse.GetMine.builder()
                .id(wishlist.getId())
                .name(wishlist.getName())
                .images(wishlist.getWishlistPlaces().stream().map(wishlistPlace -> wishlistPlace.getPlace().getThumbnail().getUrl()).map(FileDto.Image::new).collect(Collectors.toList()))
                .build())
                .collect(Collectors.toList());
    }

    default WishlistResponse.GetPlaces toGetPlaces(List<WishlistPlace> wishlistPlaces) {
        return WishlistResponse.GetPlaces.builder()
                .places(wishlistPlaces.stream().map(wishlistPlace -> WishlistDto.Place.builder()
                        .id(wishlistPlace.getPlace().getId())
                        .name(wishlistPlace.getPlace().getName())
                        .image(wishlistPlace.getPlace().getThumbnail().getUrl())
                        .build())
                        .collect(Collectors.toList())).build();
    }
}
