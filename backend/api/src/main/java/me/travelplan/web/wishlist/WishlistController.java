package me.travelplan.web.wishlist;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.wishlist.WishlistService;
import me.travelplan.web.wishlist.dto.WishlistRequest;
import me.travelplan.web.wishlist.dto.WishlistResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WishlistResponse.GetOnlyId create(@RequestBody WishlistRequest.Create request) {
        return wishlistMapper.toWishlistId(wishlistService.create(request));
    }

    @PostMapping("/{wishlistId}/place")
    public void addPlace(@PathVariable Long wishlistId, @RequestBody WishlistRequest.AddPlace request, @CurrentUser CustomUserDetails customUserDetails) {
        wishlistService.addPlace(wishlistId, request, customUserDetails.getUser());
    }

    @GetMapping("/my")
    public List<WishlistResponse.GetMine> getMine(@CurrentUser CustomUserDetails customUserDetails) {
        return wishlistMapper.toGetMine(wishlistService.getMine(customUserDetails.getUser()));
    }

    @GetMapping("/{wishlistId}/places")
    public WishlistResponse.GetPlaces getPlaces(@PathVariable Long wishlistId) {
        return wishlistMapper.toGetPlaces(wishlistService.getPlaces(wishlistId));
    }
}
