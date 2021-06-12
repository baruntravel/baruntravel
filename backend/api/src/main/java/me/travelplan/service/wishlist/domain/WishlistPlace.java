package me.travelplan.service.wishlist.domain;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.place.domain.Place;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wishlist_places")
public class WishlistPlace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
        wishlist.getWishlistPlaces().add(this);
    }

    public static WishlistPlace create(Wishlist wishlist, Place place) {
        WishlistPlace wishlistPlace = WishlistPlace.builder().place(place).build();
        wishlistPlace.setWishlist(wishlist);
        return wishlistPlace;
    }
}
