package me.travelplan.service.cart;

import me.travelplan.web.cart.CartDto;
import me.travelplan.web.cart.CartResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CartMapper {
    default CartResponse.GetList toGetListResponse(Cart cart) {

        List<CartDto.CartPlace> cartPlaces = new ArrayList<>();
        cart.getCartPlaces().forEach(cartPlace -> {
            CartDto.CartPlace place = CartDto.CartPlace.builder()
                    .id(cartPlace.getPlace().getId())
                    .name(cartPlace.getPlace().getName())
                    .category(cartPlace.getPlace().getCategory().getName())
                    .image(cartPlace.getPlace().getImage().getUrl())
                    .url(cartPlace.getPlace().getUrl())
                    .build();
            cartPlaces.add(place);

        });
        return CartResponse.GetList.builder()
                .places(cartPlaces)
                .build();
    }
}
