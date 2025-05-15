package com.availabilitySchedule.dto;

import com.availabilitySchedule.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailabilityDto {
	private String doctorId;
	private String doctorName;
	private Specialization specialization;
}