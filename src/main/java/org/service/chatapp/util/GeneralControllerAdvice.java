package org.service.chatapp.util;

import org.service.chatapp.exception.ResourceAlreadyExistException;
import org.service.chatapp.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

/**
 * General advice for all controllers.
 * It handles exceptions that are thrown from the controllers.
 */
@ControllerAdvice
public class GeneralControllerAdvice {

	/**
	 * Handles validation exceptions.
	 * It returns a response with a BAD_REQUEST status and the exception message.
	 *
	 * @param e The validation exception.
	 * @return The error response.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody
	ErrorResponse notFoundExceptionHandler(ResourceNotFoundException e) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	/**
	 * Handles validation exceptions.
	 * It returns a response with a BAD_REQUEST status and the exception message.
	 *
	 * @param e The validation exception.
	 * @return The error response.
	 */
	@ExceptionHandler(ResourceAlreadyExistException.class)
	@ResponseStatus(HttpStatus.ALREADY_REPORTED)
	public @ResponseBody
	ErrorResponse resourceAlreadyExistExceptionHandler(ResourceAlreadyExistException e) {
		return new ErrorResponse(HttpStatus.ALREADY_REPORTED.value(), e.getMessage());
	}


	/**
	 * Handles all other exceptions.
	 * @param e
	 * @return The error response.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody
	ErrorResponse exceptionHandler(Exception e) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}
}
