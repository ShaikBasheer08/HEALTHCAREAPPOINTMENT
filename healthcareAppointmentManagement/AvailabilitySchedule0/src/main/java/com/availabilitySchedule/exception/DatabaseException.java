package com.availabilitySchedule.exception;

/**
 * Exception thrown when there is a database error.

 */
public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}