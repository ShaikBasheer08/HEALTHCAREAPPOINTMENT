package com.availabilitySchedule.exception;

/**
 * Exception thrown when a requested resource is unavailable.
 */

public class UnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnavailableException(String message) {
		super(message);
	}
}
