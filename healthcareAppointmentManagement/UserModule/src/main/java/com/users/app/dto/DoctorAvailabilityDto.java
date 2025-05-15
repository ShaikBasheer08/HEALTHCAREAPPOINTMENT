package com.users.app.dto;
 
import com.users.app.enums.Specialization;
import com.users.app.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailabilityDto {
    private Long doctorId;
    private String doctorName;
    private Specialization specialization;
 
    public Doctor toEntity() { 
        Doctor doctor = new Doctor();
        doctor.setDoctorId(this.doctorId);
        doctor.setName(this.doctorName);
        doctor.setSpecialization(this.specialization);
        return doctor;
    }
 
    public static DoctorAvailabilityDto fromEntity(Doctor doctor) {
        return new DoctorAvailabilityDto(doctor.getDoctorId(),doctor.getName(), doctor.getSpecialization());
    }
}