package me.travelplan.exception;

import me.travelplan.exception.BusinessException;

public class PermissionDeniedException extends BusinessException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
