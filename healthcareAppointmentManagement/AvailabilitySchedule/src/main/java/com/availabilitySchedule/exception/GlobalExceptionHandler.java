package com.availabilitySchedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AvailabilityNotFoundException.class)
	public ResponseEntity<String> handleNoAvailabilityFoundException(AvailabilityNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UnavailableException.class)
	public ResponseEntity<String> handleUnavailableException(UnavailableException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DoctorNotFoundException.class)
	public ResponseEntity<String> handleDoctorNotAvailableException(DoctorNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}