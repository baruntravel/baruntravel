package me.travelplan.web.wishlist;

import me.travelplan.service.wishlist.domain.Wishlist;
import me.travelplan.service.wishlist.domain.WishlistPlace;
import me.travelplan.web.wishlist.dto.WishlistResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface WishlistMapper {
    WishlistResponse.GetOnlyId toWishlistId(Wishlist wishlist);
    WishlistResponse.GetOnlyWishlistPlaceId toWishlistPlaceId(WishlistPlace wishlistPlace);
}
