package com.healthcare.appointment.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.healthcare.appointment.dto.Response;
import com.healthcare.appointment.exception.AppointmentNotFoundException;
import com.healthcare.appointment.exception.AvailabilityConflictException;
import com.healthcare.appointment.exception.AvailabilityNotFoundException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response<?>> handleAppointmentNotFoundException(AppointmentNotFoundException ex) {
        log.error("Exception: {}", ex.getMessage());
        Response<?> response = new Response<>(false, HttpStatus.NOT_FOUND, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AvailabilityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response<?>> handleAvailabilityNotFoundException(AvailabilityNotFoundException ex) {
        log.error("Exception: {}", ex.getMessage());
        Response<?> response = new Response<>(false, HttpStatus.NOT_FOUND, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AvailabilityConflictException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response<?>> handleAvailabilityConflictException(AvailabilityConflictException ex) {
        log.error("Exception: {}", ex.getMessage());
        Response<?> response = new Response<>(false, HttpStatus.NOT_FOUND, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
   
}