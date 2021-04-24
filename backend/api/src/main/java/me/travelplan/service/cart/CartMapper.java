package me.travelplan.service.cart;

import me.travelplan.web.cart.CartPlaceDto;
import me.travelplan.web.cart.CartPlaceResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CartMapper {
    default CartPlaceResponse.GetList toGetListResponse(List<CartPlace> cartPlaces) {
        List<CartPlaceDto.Place> cartPlaceList = new ArrayList<>();
        cartPlaces.forEach(cartPlace -> {
            CartPlaceDto.Place place = CartPlaceDto.Place.builder()
                    .id(cartPlace.getPlace().getId())
                    .name(cartPlace.getPlace().getName())
                    .category(cartPlace.getPlace().getCategory().getName())
                    .image(cartPlace.getPlace().getImage().getUrl())
                    .url(cartPlace.getPlace().getUrl())
                    .build();
            cartPlaceList.add(place);
        });

        return CartPlaceResponse.GetList.builder()
                .places(cartPlaceList)
                .build();
    }
}
