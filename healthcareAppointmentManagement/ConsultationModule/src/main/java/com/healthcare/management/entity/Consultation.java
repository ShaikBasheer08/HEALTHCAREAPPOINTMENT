package com.healthcare.management.entity;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationId; 
    
    @NotNull
    @Column(name = "appointment_id", nullable = false)
    private Long appointmentId; // ✅ Ensure appointment reference is mandatory
    
    @Size(max = 500)
    @Column(name = "notes", nullable = true, length = 500)
    private String notes; 
    
    @NotNull
    @Column(name = "prescription", nullable = false)
    private String prescription;
}
