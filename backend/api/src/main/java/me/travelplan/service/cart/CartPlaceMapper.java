package me.travelplan.service.cart;

import me.travelplan.service.place.Place;
import me.travelplan.service.user.User;
import me.travelplan.web.cart.CartPlaceDto;
import me.travelplan.web.cart.CartPlaceRequest;
import me.travelplan.web.cart.CartPlaceResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CartPlaceMapper {
    @Mapping(constant = "PENDING", target = "detailStatus")
    @Mapping(source = "place.categoryId", target = "category.id")
    @Mapping(source = "place.categoryName", target = "category.name")
    @Mapping(constant = "", target = "openHour")
    Place dtoToPlace(CartPlaceRequest.AddPlace place);

    default CartPlaceResponse.GetList toGetListResponse(List<CartPlace> cartPlaces, User user) {
        List<CartPlaceDto.Place> cartPlaceList = new ArrayList<>();
        cartPlaces.forEach(cartPlace -> {
            CartPlaceDto.Place place = CartPlaceDto.Place.builder()
                    .id(cartPlace.getPlace().getId())
                    .name(cartPlace.getPlace().getName())
                    .address(cartPlace.getPlace().getAddress())
                    .categoryId(cartPlace.getPlace().getCategory().getId())
                    .categoryName(cartPlace.getPlace().getCategory().getName())
                    .memo(cartPlace.getMemo())
                    .likeCheck(cartPlace.getPlace().isLike(user))
                    .url(cartPlace.getPlace().getUrl())
                    .build();
            cartPlaceList.add(place);
        });

        return CartPlaceResponse.GetList.builder()
                .places(cartPlaceList)
                .build();
    }
}
