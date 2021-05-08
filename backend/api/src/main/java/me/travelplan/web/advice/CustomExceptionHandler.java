package me.travelplan.web.advice;

import me.travelplan.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status("400")
                .error("BadRequest")
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
