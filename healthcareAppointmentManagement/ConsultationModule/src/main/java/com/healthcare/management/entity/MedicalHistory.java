package com.healthcare.management.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="MedicalHistory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "getMedicalHistoryByPatientId",
query = "SELECT h FROM MedicalHistory h WHERE h.patientId = :patientId")
public class MedicalHistory {
	
	@Id
	@Column(name="History_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long historyId;	
	
	
	
	@Column(name = "Patient_id")
	private Long patientId;
	
	
	
	@Column(name="DiseaseHistory")
	private String healthHistory;
	
	
	
}
