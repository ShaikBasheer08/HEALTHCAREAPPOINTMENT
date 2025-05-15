package com.cts.healthcareappointment.notificationmodule.dto;

import java.time.LocalDate;

import com.cts.healthcareappointment.notificationmodule.Entity.Status;
import com.cts.healthcareappointment.notificationmodule.Entity.TimeSlots;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointmentdto {


	private String appointmentId;
    private TimeSlots timeSlot;
    private Status status;
    private String doctorId;
    private String doctorName;
    private String patientName;
    private String patientId;
    private LocalDate date;
    private String availabilityId;
    
}