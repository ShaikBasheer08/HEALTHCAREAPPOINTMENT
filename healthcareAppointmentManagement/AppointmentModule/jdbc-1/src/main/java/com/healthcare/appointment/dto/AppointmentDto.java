package com.healthcare.appointment.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import com.healthcare.appointment.model.Status;
import com.healthcare.appointment.model.TimeSlots;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class AppointmentDto {

    @NotNull
    private Long appointmentId;

    @NotNull
    private TimeSlots timeSlot;

    @NotNull
    private Status status;

    @NotNull
    private Long doctorId;

    @NotNull
    private LocalDate date;

    @NotNull
    private Long availabilityId;

    @NotNull
    private Long patientId;

    @NotNull
    private String patientName;

    @NotNull
    private String doctorName;
}
