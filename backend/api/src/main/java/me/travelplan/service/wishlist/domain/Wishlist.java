package me.travelplan.service.wishlist.domain;

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
@Table(name = "wishlist")
public class Wishlist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "wishlist")
    @Builder.Default
    private final List<WishlistPlace> wishlistPlaces = new ArrayList<>();

    public static Wishlist create(String name) {
        return Wishlist.builder().name(name).build();
    }
}
