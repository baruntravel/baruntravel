package me.travelplan.service.cart.domain;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.domain.Place;

import javax.persistence.*;

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

    public void addMemo(String memo) {
        this.memo = memo;
    }

    public static CartPlace create(Place place, Integer maxOrder) {
        return CartPlace.builder()
                .place(place)
                .order(maxOrder + 1)
                .build();
    }

    private void updateOrder(Integer order) {
        this.order = order;
    }

    public static void swapOrder(CartPlace firstCartPlace, CartPlace secondCartPlace) {
        Integer tempOrder = firstCartPlace.getOrder();
        firstCartPlace.updateOrder(secondCartPlace.getOrder());
        secondCartPlace.updateOrder(tempOrder);
    }
}
