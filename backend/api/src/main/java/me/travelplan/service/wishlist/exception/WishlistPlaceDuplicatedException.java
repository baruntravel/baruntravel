package me.travelplan.service.wishlist.exception;

import me.travelplan.exception.BusinessException;

public class WishlistPlaceDuplicatedException extends BusinessException {
    public WishlistPlaceDuplicatedException() {
        super("찜목록에 이미 존재하는 장소입니다.");
    }
}
