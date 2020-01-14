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

@Order(Ordered.HIGHEST_PRECEDENCE) //Ordering priority while injecting this class as dependency
@ControllerAdvice //for automatic scanning via Classpath Scanning 
@RestController //Controller+ResponseBody
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({BookCopiesNotAvailableException.class, SameBookAlreadyTakenException.class,
		BookAlreadyReturnedException.class})
	@ResponseBody //converts the return value into response body(optional at method level after Spring v4.0)
	public ResponseEntity<Object> handleBookCopiesNotAvailableException(
			Exception ex){
		System.out.println("Exception: " + ex.getMessage());
		//Creates a new Response Entity object, wraps exception message and HttpStatus
				//of NOT_FOUND and returns it.
		return new ResponseEntity<>(ex.getMessage(),
				HttpStatus.NOT_FOUND);
	}
}
