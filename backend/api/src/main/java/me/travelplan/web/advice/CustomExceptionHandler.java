package me.travelplan.web.advice;

import me.travelplan.exception.BusinessException;
import me.travelplan.service.exception.ResponsibleClientException;
import me.travelplan.service.exception.ResponsibleServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity businessExceptionHandler(BusinessException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", 400);
        response.put("error", "Bad request");
        response.put("message", ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponsibleClientException.class)
    public ResponseEntity responsibleClientException(ResponsibleClientException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", 400);
        response.put("error", "Bad request");
        response.put("message", ex.getMessage());

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponsibleServerException.class)
    public ResponseEntity responsibleServerException(ResponsibleServerException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", 500);
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
