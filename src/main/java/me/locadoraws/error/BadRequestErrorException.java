package me.locadoraws.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alex Junior Rambo 
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestErrorException extends RuntimeException{
	
	public BadRequestErrorException(String message) {		
		super(message);
	}
}
