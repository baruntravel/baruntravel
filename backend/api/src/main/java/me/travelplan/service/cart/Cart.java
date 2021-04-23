package me.travelplan.service.cart;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<CartPlace> cartPlaces = new ArrayList<>();

    private void addCartPlace(CartPlace cartPlace) {
        cartPlace.addCart(this);
    }

    public static Cart create(CartPlace cartPlace) {
        Cart cart = Cart.builder().build();
        cart.addCartPlace(cartPlace);
        return cart;
    }
}
