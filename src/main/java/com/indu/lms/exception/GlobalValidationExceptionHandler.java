package com.indu.lms.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalValidationExceptionHandler {

	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();

	        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	            errors.put(error.getField(), error.getDefaultMessage());
	        }
	        System.out.println("Validation");
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
}

