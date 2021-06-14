package me.travelplan.service.wishlist.exception;

import me.travelplan.exception.EntityNotFoundException;

public class WishlistNotFoundException extends EntityNotFoundException {
    public WishlistNotFoundException() {
        super("찾을 수 없는 찜목록입니다.");
    }
}
