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
	
	@ExceptionHandler(BookCopiesNotAvailableException.class)
	@ResponseBody
	public ResponseEntity<Object> handleBookCopiesNotAvailableException(
			BookCopiesNotAvailableException ex){
		System.out.println("Exception: " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SameBookAlreadyTakenException.class)
	@ResponseBody
	public ResponseEntity<Object> handleSameBookAlreadyTakenException(
			SameBookAlreadyTakenException ex){
		System.out.println("Exception: " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BookAlreadyReturnedException.class)
	@ResponseBody
	public ResponseEntity<Object> handleBookAlreadyReturnedException(
			BookAlreadyReturnedException ex){
		System.out.println("Exception: " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(),
				HttpStatus.NOT_FOUND);
	}
}
