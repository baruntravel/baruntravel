package me.travelplan.web.cart;

import me.travelplan.service.cart.domain.CartPlace;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.user.domain.User;
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
                    .likeCheck(cartPlace.getPlace().isLike(user))
                    .url(cartPlace.getPlace().getUrl())
                    .x(cartPlace.getPlace().getX())
                    .y(cartPlace.getPlace().getY())
                    .memo(cartPlace.getMemo())
                    .order(cartPlace.getOrder())
                    .build();
            cartPlaceList.add(place);
        });

        return CartPlaceResponse.GetList.builder()
                .places(cartPlaceList)
                .build();
    }
}
