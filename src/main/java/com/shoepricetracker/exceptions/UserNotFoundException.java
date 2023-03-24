package com.shoepricetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1l;
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
