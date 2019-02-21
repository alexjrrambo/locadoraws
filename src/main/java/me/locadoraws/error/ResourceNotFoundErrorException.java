package me.locadoraws.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alex Junior Rambo 
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundErrorException extends RuntimeException {
	
	public ResourceNotFoundErrorException(String message) {
		super(message);
	}
}
