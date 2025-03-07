package com.indu.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Internal Server Error");
	        errorResponse.put("message", ex.getMessage());
	        System.out.println("excep not found");
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 
//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
//    	System.out.println("excep not found");
//        return new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.NOT_FOUND.value(),
//                "Not Found",
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleGlobalException(Exception ex, HttpServletRequest request) {
//    	System.out.println("excep server err");
//        return new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Internal Server Error",
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//    }
    
//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleResourceNotFoundException(ResourceNotFoundException ex) {
//        return ex.getMessage();
//    }
}


