package org.service.chatapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ResourceAlreadyExistException extends HttpStatusCodeException {
	public ResourceAlreadyExistException(String message) {
		super(HttpStatus.ALREADY_REPORTED, message);
	}
}
