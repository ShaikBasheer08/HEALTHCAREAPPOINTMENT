package com.healthcare.appointment.repository;

/**
* Repository for AppointmentTable
* 
* @Author Sanjay R
* @Since 2025-03-18
*/
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.appointment.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	Optional<Appointment> findByAvailabilityId(Long availabilityId);

	List<Appointment> findByPatientId(Long patientId);

	List<Appointment> findByDoctorId(Long doctorId);
	

	
}