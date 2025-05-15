package com.healthcare.management.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoConsultationDetailsFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public NoConsultationDetailsFoundException(String msg) {
		super(msg);
		log.debug("No consultation details found..");
	}
}
