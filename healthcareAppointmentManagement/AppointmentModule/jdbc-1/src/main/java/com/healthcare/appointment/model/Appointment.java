package com.healthcare.appointment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Enables auto-incremented ID generation
    @Column(name = "appointment_id", nullable = false, unique = true)
    private Long appointmentId; // Changed from String to Long for numerical ID generation

    @Enumerated(EnumType.STRING)
    @Column(name = "time_slot")
    private TimeSlots timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "patientname", nullable = true)
    private String patientName;

    @Column(name = "doctorname", nullable = true)
    private String doctorName;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "availability_id", nullable = false, unique = true)
    private Long availabilityId;
}
