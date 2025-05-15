package com.availabilitySchedule.exception;

/**
 * Exception thrown when a doctor is not found.

 */

public class DoctorNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DoctorNotFoundException(String message) {
		super(message);
	}
}
