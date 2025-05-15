package com.healthcare.appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class AvailabilityConflictException extends RuntimeException {
	public AvailabilityConflictException(String msg) {
		super(msg);
	}

}
