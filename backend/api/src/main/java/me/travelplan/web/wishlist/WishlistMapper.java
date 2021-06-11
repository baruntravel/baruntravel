package me.travelplan.web.wishlist;

import me.travelplan.service.wishlist.domain.Wishlist;
import me.travelplan.web.wishlist.dto.WishlistResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface WishlistMapper {
    WishlistResponse.GetOnlyId toWishlistIdResponse(Wishlist wishlist);
}
