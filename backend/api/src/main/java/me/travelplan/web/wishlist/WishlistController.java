package me.travelplan.web.wishlist;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.wishlist.WishlistService;
import me.travelplan.web.wishlist.dto.WishlistRequest;
import me.travelplan.web.wishlist.dto.WishlistResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;

    @PostMapping
    public WishlistResponse.GetOnlyId create(@RequestBody WishlistRequest.Create request) {
        return wishlistMapper.toWishlistIdResponse(wishlistService.create(request));
    }
}
