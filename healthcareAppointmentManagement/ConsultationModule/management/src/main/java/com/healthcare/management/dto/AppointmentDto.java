package com.healthcare.management.dto;

import lombok.Data;

/**
 * DTO for fetching appointment details from the Appointment Microservice via Feign Client.
 */
@Data
public class AppointmentDto {
    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private String date;
    private String status;
}
