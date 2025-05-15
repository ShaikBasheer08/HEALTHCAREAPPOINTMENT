package com.availabilitySchedule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Entity representing availability.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated ID
    @Column(name = "availability_id", nullable = false, unique = true)
    private Long availabilityId; // Changed to Long for auto-increment

    @Column(name = "doctor_id", nullable = true)
    private Long doctorId;

    @Column(name = "doctor_name", nullable = true)
    private String doctorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization", nullable = true)
    private Specialization specialization;

    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot")
    private Timeslots timeSlots;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
