package com.healthcare.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ConsultationDto is a Data Transfer Object (DTO) that represents the details of a consultation.
 * It includes fields for consultation ID, appointment ID, notes, and prescription.
 * 
 * @Data - Generates getters, setters, toString, equals, and hashCode methods.
 * @NoArgsConstructor - Generates a no-argument constructor.
 * @AllArgsConstructor - Generates an all-argument constructor.
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDto {
    private Long consultationId;
    
    @NotNull(message="Appointment ID cannot be null")
    private Long appointmentId; 
    
    @Size(max = 500 , message="Notes cannot exceed more than 500 characters")
    @NotBlank(message="Notes cannot be left blank")
    @NotNull(message="Notes cannot be null")
    private String notes;
    
    @Size(max=1000,message="Prescription cannot exceed 1000 characters")
    @NotNull(message="Prescription cannot be null")
    @NotBlank(message="Prescription cannot be left blank")
    private String prescription;
    
    
    
}