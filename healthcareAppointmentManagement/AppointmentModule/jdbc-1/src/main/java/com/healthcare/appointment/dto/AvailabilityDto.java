package com.healthcare.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import com.healthcare.appointment.model.Specialization;
import com.healthcare.appointment.model.TimeSlots;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class AvailabilityDto {
	@NotEmpty
    private Long availabilityId;
	@NotNull
    private TimeSlots timeSlots;
	@NotNull
    private LocalDate date;
	@NotEmpty
    private Long doctorId;
	@NotNull
    private Specialization specialization;
    
}
