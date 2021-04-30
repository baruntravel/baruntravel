package me.travelplan.service.user.exception;

import me.travelplan.exception.BusinessException;

public class EmailExistedException extends BusinessException {
    public EmailExistedException() {
        super("중복된 이메일입니다");
    }
}
