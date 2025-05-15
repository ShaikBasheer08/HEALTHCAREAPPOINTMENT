package com.healthcare.management.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HistoryDto is a Data Transfer Object (DTO) that represents the details of a medical history.
 * It includes fields for history ID, patient ID, and health history.
 * 
 * @Data - Generates getters, setters, toString, equals, and hashCode methods.
 * @NoArgsConstructor - Generates a no-argument constructor.
 * @AllArgsConstructor - Generates an all-argument constructor.
 */



@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {
    private Long historyId;
    
    @NotNull(message="Patient ID cannot be null")
    private Long patientId; 
    
    //private String patientName;
    
    @Size(max=500,message="Medical history do not exceed more than 500 characters")
    private String healthHistory;
}