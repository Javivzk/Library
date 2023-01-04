package com.svalero.library.util;

import com.svalero.library.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

public class ErrorExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ErrorExceptionUtil.class);
    public static ResponseEntity<ErrorMessage> getErrorExceptionResponseEntity(ConstraintViolationException cve) {
        Map<String, String> errors = new HashMap<>();
        cve.getConstraintViolations().forEach(error -> {
            logger.error(error.getMessage());
            String fieldName = error.getMessage();
            String name = error.toString();
            errors.put(fieldName, name);
        });
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error", errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ErrorMessage> getErrorExceptionResponseEntity(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String name = error.getDefaultMessage();
            errors.put(fieldName, name);
            logger.error("Error: " + fieldName + " " + name);
        });
        ErrorMessage errorMessage = new ErrorMessage(403, "Forbidden", errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }
}
