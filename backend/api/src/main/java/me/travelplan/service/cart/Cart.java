package me.travelplan.service.cart;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.user.User;

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

    public void addPlace(CartPlace cartPlace){
        this.cartPlaces.add(cartPlace);
        cartPlace.setCart(this);
    }

    public static Cart createEmpty(User user) {
        Cart cart = Cart.builder().build();
        cart.setCreatedBy(user);
        cart.setUpdatedBy(user);
        return cart;
    }
}
