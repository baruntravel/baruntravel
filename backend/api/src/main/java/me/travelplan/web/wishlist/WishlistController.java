package me.travelplan.web.wishlist;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.wishlist.WishlistService;
import me.travelplan.web.wishlist.dto.WishlistRequest;
import me.travelplan.web.wishlist.dto.WishlistResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;

    @PostMapping
    public WishlistResponse.GetOnlyId create(@RequestBody WishlistRequest.Create request) {
        return wishlistMapper.toWishlistId(wishlistService.create(request));
    }

    @PostMapping("/{wishlistId}/place")
    public WishlistResponse.GetOnlyWishlistPlaceId addPlace(@PathVariable Long wishlistId, @RequestBody WishlistRequest.AddPlace request, @CurrentUser CustomUserDetails customUserDetails) {
        return wishlistMapper.toWishlistPlaceId(wishlistService.addPlace(wishlistId, request, customUserDetails.getUser()));
    }
}
