package com.availabilitySchedule.dto;

import com.availabilitySchedule.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailabilityRequest {
    private Long doctorId;
    private String doctorName;
    private Specialization specialization;
    private List<AvailabilityDTO> availability;
}
