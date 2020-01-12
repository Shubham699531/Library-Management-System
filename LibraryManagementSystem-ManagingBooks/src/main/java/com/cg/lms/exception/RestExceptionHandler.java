package com.cg.lms.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({BookNotFoundException.class, BookAlreadyTakenBySomeoneException.class})
	@ResponseBody
	public ResponseEntity<Object> handleBookNotFoundException(
			Exception ex){
		System.out.println("Exception: " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(),
				HttpStatus.NOT_FOUND);
	}
}
