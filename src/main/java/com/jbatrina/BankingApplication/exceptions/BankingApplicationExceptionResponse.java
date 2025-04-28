package com.jbatrina.BankingApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BankingApplicationExceptionResponse {
    @ResponseBody
    @ExceptionHandler(BankingApplicationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<String> BankingApplicationExceptionHandler(BankingApplicationException exception) {
    	exception.printStackTrace();
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.getContextMessage());
    }
    
    @ResponseBody
    @ExceptionHandler(java.sql.SQLException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<String> SQLErrorHandler(java.sql.SQLException exception) {
    	exception.printStackTrace();
 
    	if (exception instanceof java.sql.SQLIntegrityConstraintViolationException
    		&& exception.getMessage().contains("Duplicate")
    		&& exception.getMessage().contains("departments.")
		) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("A department with the same name already exists");
    	}
    	
    	return null;
    }
}
