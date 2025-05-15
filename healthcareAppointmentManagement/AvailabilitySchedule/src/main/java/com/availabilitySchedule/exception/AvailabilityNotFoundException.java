package com.availabilitySchedule.exception;

/**
 * Exception thrown when availability is not found.

 */
public class AvailabilityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AvailabilityNotFoundException(String message) {
		super(message);
	}
}