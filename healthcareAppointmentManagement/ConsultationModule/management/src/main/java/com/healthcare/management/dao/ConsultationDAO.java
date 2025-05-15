package com.healthcare.management.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.healthcare.management.entity.Consultation;

/**
 * ConsultationDAO is a Data Access Object (DAO) interface for managing consultation records.
 * It extends JpaRepository to provide CRUD operations and custom query methods for Consultation entities.
 */

public interface ConsultationDAO extends JpaRepository<Consultation, Long> {
    
    /**
     * Retrieves consultation records filtered by appointment ID.
     */
    @Query("SELECT c FROM Consultation c WHERE c.appointmentId = :appointmentId")
    List<Consultation> findConsultationDetailsByAppointmentId(@Param("appointmentId") Long appointmentId);
    
    /**
     * Retrieves a consultation record for a specific appointment ID.
     */
    Optional<Consultation> findConsultationByAppointmentId(Long appointmentId);

    /**
     * Retrieves a consultation record by consultation ID.
     */
    Optional<Consultation> findByConsultationId(Long consultationId);
    
    /**
     * Checks if a consultation exists for a given appointment ID.
     */
    boolean existsByAppointmentId(Long appointmentId);
}
