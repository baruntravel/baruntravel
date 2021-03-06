package me.travelplan.service.cart.domain;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.domain.Place;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "carts_places")
public class CartPlace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memo;

    @Column(name = "`order`")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private void updateOrder(Integer order) {
        this.order = order;
    }

    public void addMemo(String memo) {
        this.memo = memo;
    }

    public static CartPlace create(Place place, Integer maxOrder) {
        return CartPlace.builder()
                .place(place)
                .order(maxOrder + 1)
                .build();
    }

    public static void updateAllOrder(List<CartPlace> cartPlaces) {
        int i = 1;
        for (CartPlace cartPlace : cartPlaces) {
            cartPlace.updateOrder(i++);
        }
    }

    public static void swapOrder(CartPlace firstCartPlace, CartPlace secondCartPlace) {
        Integer tempOrder = firstCartPlace.getOrder();
        firstCartPlace.updateOrder(secondCartPlace.getOrder());
        secondCartPlace.updateOrder(tempOrder);
    }
}
