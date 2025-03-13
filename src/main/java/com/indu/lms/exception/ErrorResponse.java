package com.indu.lms.exception;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
   
    private HttpStatus status;
    
    private String message;
    

}
