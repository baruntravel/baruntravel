package me.travelplan.service.cart;

import lombok.*;
import me.travelplan.service.place.Place;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "carts_places")
public class CartPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;


    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public static CartPlace createWithCart(Place place, Cart cart) {
        CartPlace cartPlace = CartPlace.builder()
                .place(place)
                .build();
        cart.addPlace(cartPlace);

        return cartPlace;
    }
}
