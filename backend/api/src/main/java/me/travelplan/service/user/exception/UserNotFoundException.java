package me.travelplan.service.user.exception;

import me.travelplan.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super("해당 유저를 찾을 수 없습니다");
    }
}
