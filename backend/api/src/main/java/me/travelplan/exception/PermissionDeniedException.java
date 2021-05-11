package me.travelplan.exception;

public class PermissionDeniedException extends BusinessException {
    public PermissionDeniedException() {
        super("권한이 없습니다");
    }

    public PermissionDeniedException(String message) {
        super(message);
    }
}
