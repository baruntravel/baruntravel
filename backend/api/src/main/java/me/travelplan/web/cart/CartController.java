package me.travelplan.web.cart;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.cart.CartPlaceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartPlaceService cartPlaceService;
    private final CartPlaceMapper cartMapper;

    @PostMapping("/place")
    public void addPlace(@RequestBody CartPlaceRequest.AddPlace request, @CurrentUser CustomUserDetails customUserDetails) {
        cartPlaceService.addPlace(cartMapper.dtoToPlace(request), customUserDetails.getUser());
    }

    @GetMapping("/my")
    public CartPlaceResponse.GetList getMyCart(@CurrentUser CustomUserDetails customUserDetails) {
        return cartMapper.toGetListResponse(cartPlaceService.getMyCart(customUserDetails.getUser()), customUserDetails.getUser());
    }

    @PutMapping("/place")
    public void updatePlaceOrder(@RequestBody CartPlaceRequest.UpdateOrder request, @CurrentUser CustomUserDetails customUserDetails) {
        cartPlaceService.updatePlaceOrder(request, customUserDetails.getUser());
    }

    @PutMapping("/place/{placeId}/memo")
    public void addMemo(@PathVariable Long placeId, @RequestBody CartPlaceRequest.AddMemo request, @CurrentUser CustomUserDetails customUserDetails) {
        cartPlaceService.addMemo(placeId, request, customUserDetails.getUser());
    }

    @DeleteMapping("/place/{placeId}")
    public void deleteOnePlace(@PathVariable Long placeId, @CurrentUser CustomUserDetails customUserDetails) {
        cartPlaceService.deleteOnePlace(placeId, customUserDetails.getUser());
    }

    @DeleteMapping("/place")
    public void deleteAllPlace(@CurrentUser CustomUserDetails customUserDetails) {
        cartPlaceService.deleteAllPlace(customUserDetails.getUser());
    }
}
