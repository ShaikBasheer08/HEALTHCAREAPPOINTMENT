package com.availabilitySchedule.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import com.availabilitySchedule.model.Availability;
import java.util.List;
import com.availabilitySchedule.model.Specialization;
import com.availabilitySchedule.model.Status;
import com.availabilitySchedule.model.Timeslots;

/**
 * Repository interface for Availability entities.
 * 
 * Provides methods to perform CRUD operations and custom queries on
 * Availability entities.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.availabilitySchedule.model.Availability
 
 */
@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    /**
     * Finds a list of availabilities by doctor ID and date.
     * 
     * @param doctorId the ID of the doctor
     * @param date     the date of the availability
     * @return a list of availabilities for the specified doctor and date
     */
    List<Availability> findByDoctorIdAndDate(Long doctorId, LocalDate date);

    /**
     * Finds a list of availabilities by doctor ID.
     * 
     * @param doctorId the ID of the doctor
     * @return the list of availabilities for the specified doctor
     */
    List<Availability> findByDoctorId(Long doctorId);

    /**
     * Finds a list of available slots for a given doctor.
     * 
     * @param doctorId the ID of the doctor
     * @return a list of available slots
     */
    List<Availability> findByDoctorIdAndStatus(Long doctorId, Status status);

    /**
     * Finds a list of availabilities by specialization and date.
     * 
     * @param specialization the specialization of the doctor
     * @param date           the date of the availability
     * @return a list of availabilities for the specified specialization and date
     */
    List<Availability> findBySpecializationAndDate(Specialization specialization, LocalDate date);

    /**
     * Finds available slots for a given specialization and date.
     * 
     * @param specialization the specialization of the doctor
     * @param date           the date of the availability
     * @param status         the status of the slot
     * @return a list of available slots
     */
    List<Availability> findBySpecializationAndDateAndStatus(Specialization specialization, LocalDate date, Status status);

    /**
     * Finds a list of availabilities with dates before the specified date.
     * 
     * @param date the date to compare
     * @return a list of availabilities with dates before the specified date
     */
    List<Availability> findByDateBefore(LocalDate date);

    /**
     * Finds a list of availabilities by doctor ID and date range.
     * 
     * @param doctorId  the ID of the doctor
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return a list of availabilities for the specified doctor and date range
     */
    List<Availability> findByDoctorIdAndDateBetween(Long doctorId, LocalDate startDate, LocalDate endDate);

    /**
     * Finds a list of availabilities by specialization and date range.
     * 
     * @param specialization the specialization of the doctor
     * @param startDate      the start date of the range
     * @param endDate        the end date of the range
     * @return a list of availabilities for the specified specialization and date range
     */
    List<Availability> findBySpecializationAndDateBetween(Specialization specialization, LocalDate startDate, LocalDate endDate);

    /**
     * Checks if availability exists for a given date range.
     * 
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return true if availability exists within the date range, false otherwise
     */
    

    
    boolean existsByDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Checks if a specific doctor's timeslot exists for a given date.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date of availability
     * @param timeslot the timeslot
     * @return true if the doctor has a slot for the given date, false otherwise
     */
    boolean existsByDoctorIdAndDateAndTimeSlots(Long doctorId, LocalDate date, Timeslots timeSlots);
}
