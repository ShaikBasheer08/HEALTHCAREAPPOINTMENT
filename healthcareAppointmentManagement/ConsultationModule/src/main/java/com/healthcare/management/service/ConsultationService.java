package com.healthcare.management.service;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.management.client.AppointmentFeignClient;
//import com.healthcare.management.client.NotificationClient;
//import com.healthcare.management.client.NotificationClient;
import com.healthcare.management.dao.ConsultationDAO;
import com.healthcare.management.dao.HistoryDAO;
import com.healthcare.management.dto.AppointmentDto;
import com.healthcare.management.dto.ConsultationDto;
import com.healthcare.management.entity.Consultation;
import com.healthcare.management.entity.MedicalHistory;
import com.healthcare.management.exception.ConsultationAlreadyExistsException;
import com.healthcare.management.exception.NoAppointmentFoundException;
import com.healthcare.management.exception.NoConsultationDetailsFoundException;
import com.healthcare.management.exception.NoHistoryFoundException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Service
public class ConsultationService {
   
    @Autowired
    private ConsultationDAO consultationDAO;
   
      
//  @Autowired
//  private NotificationClient notificationClient;
   
    @Autowired
    private HistoryDAO historyDAO;
    
    @Autowired
    private AppointmentFeignClient appointmentFeignClient;
   
    /**
     * Retrieves all consultation records.
     *
     * @return List<Consultation> - A list of all consultation records.
     */
    public List<Consultation> getAllConsultationDetails() {
        log.info("Retrieving the consultation details...");
        return consultationDAO.findAll();
    }
   
    public boolean validateAppointmentExistence(Long appointmentId) {
        try {
            AppointmentDto validAppointment = appointmentFeignClient.getAppointmentById(appointmentId);
            return validAppointment != null;
        } catch (Exception ex) {
            log.warn("Appointment validation failed for ID {}: {}", appointmentId, ex.getMessage());
            return false;
        }
    }

    public AppointmentDto getAppointmentById(Long appointmentId) {
        return appointmentFeignClient.getAppointmentById(appointmentId);


    }
    /**
     * Creates a new consultation record after validating appointment existence.
     */
    @Transactional
    public Consultation addConsultation(ConsultationDto consultationDto) {
        log.info("Creating new Consultation Record...");

        // ✅ Validate appointment existence via Feign Client before proceeding
   	 System.out.println("ConsultationService: Attempting to call AppointmentFeignClient.");

        AppointmentDto validAppointment = appointmentFeignClient.getAppointmentById(consultationDto.getAppointmentId());
        System.out.println("ConsultationService: Received response from AppointmentFeignClient: " + validAppointment); // <-- Add this line HERE, within the same block

        if (validAppointment == null) {
            throw new NoAppointmentFoundException("Appointment ID " + consultationDto.getAppointmentId() + " does not exist!");
        }

        // ✅ Check if a consultation already exists for this appointment
        boolean consultationExists = consultationDAO.existsByAppointmentId(consultationDto.getAppointmentId());
        if (consultationExists) {
            throw new ConsultationAlreadyExistsException("A consultation already exists for Appointment ID: " + consultationDto.getAppointmentId());
        }

        // ✅ Save consultation after passing all validations
        Consultation consultation = new Consultation();
        consultation.setAppointmentId(consultationDto.getAppointmentId());
        consultation.setNotes(consultationDto.getNotes());
        consultation.setPrescription(consultationDto.getPrescription());

        Consultation savedConsultation = consultationDAO.save(consultation);
        log.info("Successfully saved consultation with ID: {}", savedConsultation.getConsultationId());

        return savedConsultation;
    }

    /**
     * Retrieves consultation details by consultation ID.
     *
     * @param consultationId - The ID of the consultation to be retrieved.
     * @return Consultation - The consultation record.
     * @throws NoConsultationDetailsFoundException - If no consultation details are found for the given ID.
     */
    public Consultation getConsultationDetailsById(Long consultationId) {
        log.info("Retrieving the consultation details filtered by consultation id...");
         
        return consultationDAO.findByConsultationId(consultationId).orElseThrow(()->
                new NoConsultationDetailsFoundException("No Consultation Details found at ID"+consultationId));
       
    }
   
    /**
     * Creates a new consultation record.
     *
     * @param consultationDto - The consultation data transfer object containing the details of the consultation to be created.
     * @return Consultation - The created consultation record.
     * @throws NoAppointmentFoundException - If no appointment is found for the given appointment ID.
     * @throws ConsultationAlreadyExistsException - If a consultation already exists for the given appointment ID.
     */
   
     /*public Consultation createConsultation(ConsultationDto consultationDto) {
            log.info("Creating new Consultation Record...");
            log.info("Received ConsultationDto: {}", consultationDto);
 
            Appointment appointment = appointmentDAO.findById(consultationDto.getAppointmentId())
                    .orElseThrow(() -> new NoAppointmentFoundException("No Appointment found at ID " + consultationDto.getAppointmentId()));
 
            List<Consultation> existingConsultation = consultationDAO.findConsultationDetailsByAppointmentId(consultationDto.getAppointmentId());
            if (!existingConsultation.isEmpty()) {
                throw new ConsultationAlreadyExistsException("A consultation already exists for appointment ID " + consultationDto.getAppointmentId());
            }
 
            Consultation consultation = new Consultation();
            consultation.setAppointmentId(consultationDto.getAppointmentId());
            consultation.setNotes(consultationDto.getNotes());
            consultation.setPrescription(consultationDto.getPrescription());
           
            Consultation savedConsultation = consultationDAO.save(consultation);
       
            // Notify the notification service
            notificationClient.onCompletion(consultationDto.getAppointmentId());
 
            return savedConsultation;
           
           
           
        }*/
   
     
    /* private void updateMedicalHistory(String patientId, String consultationNotes) {
            log.info("Updating medical history for patient ID: {}", patientId);
 
            // Fetch patient's medical history
            List<MedicalHistory> medicalHistories = historyDAO.getMedicalHistoryByPatientId(patientId);
 
            if (medicalHistories.isEmpty()) {
                throw new NoHistoryFoundException("No Medical History found for Patient ID: " + patientId);
            }
 
            // Append new notes to existing history
            for (MedicalHistory history : medicalHistories) {
                String updatedHistory = history.getHealthHistory() + "\nDoctor Notes: " + consultationNotes;
                history.setHealthHistory(updatedHistory);
                historyDAO.save(history);
            }
 
            log.info("Medical history updated successfully.");
        }*/
   
   
     /**
         * Retrieves consultation details filtered by appointment ID.
         *
         * @param appointmentId - The appointment ID to filter consultation records.
         * @return List<Consultation> - A list of consultation records filtered by appointment ID.
         *//**
         * Retrieves consultation details filtered by appointment ID.
         *
         * @param appointmentId - The appointment ID to filter consultation records.
         * @return List<Consultation> - A list of consultation records filtered by appointment ID.
         */
   
    public List<Consultation>findConDetailsByAppId(Long appointmentId){
        log.info("Retrieving consultation details filtered by appointment ID...");
        List<Consultation> consultationList = consultationDAO.findConsultationDetailsByAppointmentId(appointmentId);
        if(consultationList.isEmpty()) {
            throw new NoConsultationDetailsFoundException("No Consultation found for Appointment ID: " + appointmentId);
        }
        return consultationList;
    }
   
//  public Consultation updateConsultationDetailsById(int consultationId,Consultation consultationDetails) {
//      Consultation consultation = consultationDAO.findById(consultationId).orElseThrow(()->
//          new NoConsultationDetailsFoundException("No Consultation Details found at ID"+consultationId));
//      
//      consultation.setNotes(consultationDetails.getNotes());
//      consultation.setPrescription(consultationDetails.getPrescription());
//      
//      return consultationDAO.save(consultation);
//  }
    /**
     * Updates an existing consultation record by consultation ID.
     *
     * @param consultationId - The ID of the consultation to be updated.
     * @param consultationDetails - The consultation data transfer object containing the updated details of the consultation.
     * @return Consultation - The updated consultation record.
     * @throws NoConsultationDetailsFoundException - If no consultation details are found for the given ID.
     */
    public Consultation updateConsultationDetailsById(Long consultationId, ConsultationDto consultationDetails) {
        Consultation consultation = consultationDAO.findById(consultationId).orElseThrow(() ->
            new NoConsultationDetailsFoundException("No Consultation Details found at ID " + consultationId));
 
        consultation.setNotes(consultationDetails.getNotes());
        consultation.setPrescription(consultationDetails.getPrescription());
        log.info("Successfully updated the consultation details..");
        return consultationDAO.save(consultation);
    }
   
    /** Updates consultation by appointment ID */
    public Consultation updateConsultationByAppointmentId(Long appointmentId, ConsultationDto consultationDto) {
        log.info("Updating consultation for appointment ID: {}", appointmentId);
       
        Consultation consultation = consultationDAO.findConsultationByAppointmentId(appointmentId)
            .orElseThrow(() -> new NoConsultationDetailsFoundException("No consultation found for appointment ID: " + appointmentId));
 
        consultation.setNotes(consultationDto.getNotes());
        consultation.setPrescription(consultationDto.getPrescription());
 
        log.info("Successfully updated the consultation details.");
        return consultationDAO.save(consultation); // ✅ Save the updated consultation record
    }
 
   
    /**
     * Deletes a consultation record by consultation ID.
     *
     * @param consultationId - The ID of the consultation to be deleted.
     * @throws NoConsultationDetailsFoundException - If no consultation details are found for the given ID.
     */
   
    public void deleteConsultation(Long consultationId) {
        Consultation consultation = getConsultationDetailsById(consultationId);
        consultationDAO.delete(consultation);
        log.info("Successfully removed the consultation entry");
    }
   
   
   
    /** Delete consultation details by Appointment ID */
    public void deleteConsultationByAppointmentId(Long appointmentId) {
        log.info("Attempting to delete consultation for Appointment ID: {}", appointmentId);
       
        Consultation consultation = consultationDAO.findConsultationByAppointmentId(appointmentId)
            .orElseThrow(() -> new NoConsultationDetailsFoundException("No consultation found for appointment ID: " + appointmentId));
 
        consultationDAO.delete(consultation);
        log.info("Successfully deleted consultation for Appointment ID: {}", appointmentId);
    }
 
   
   
}
 