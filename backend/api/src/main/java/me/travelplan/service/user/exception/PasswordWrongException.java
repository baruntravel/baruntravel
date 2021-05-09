package me.travelplan.service.user.exception;

import me.travelplan.exception.BusinessException;

public class PasswordWrongException extends BusinessException {
    public PasswordWrongException() {
        super("잘못된 비밀번호 입니다");
    }
}
