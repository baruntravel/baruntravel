package me.travelplan.web.cart;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.cart.CartMapper;
import me.travelplan.service.cart.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    @PostMapping
    public void addPlace(@RequestBody CartRequest.AddPlace request, @CurrentUser CustomUserDetails customUserDetails) {
        cartService.addPlace(request, customUserDetails.getUser());
    }

    @GetMapping("/my")
    public CartResponse.GetList getMyCart(@CurrentUser CustomUserDetails customUserDetails) {
        return cartMapper.toGetListResponse(cartService.getMyCart(customUserDetails.getUser()));
    }
}
