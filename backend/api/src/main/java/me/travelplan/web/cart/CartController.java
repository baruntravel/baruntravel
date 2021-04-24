package me.travelplan.web.cart;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.cart.CartMapper;
import me.travelplan.service.cart.CartPlaceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartPlaceService cartPlaceService;
    private final CartMapper cartMapper;

    @PostMapping("/place")
    public void addPlace(@RequestBody CartPlaceRequest.AddPlace request, @CurrentUser CustomUserDetails customUserDetails) {
        cartPlaceService.addPlace(request, customUserDetails.getUser());
    }

    @GetMapping("/my")
    public CartPlaceResponse.GetList getMyCart(@CurrentUser CustomUserDetails customUserDetails) {
        return cartMapper.toGetListResponse(cartPlaceService.getMyCart(customUserDetails.getUser()));
    }
}
