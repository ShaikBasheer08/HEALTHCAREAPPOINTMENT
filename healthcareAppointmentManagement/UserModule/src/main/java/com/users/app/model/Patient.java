package com.users.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.users.app.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
//import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	@Id
	private Long patientId;
	
	@OneToOne
	@MapsId
	@JsonBackReference
	@JoinColumn(name="Patient_id")
	private User userPatient;
	
	@Column(name ="Name",nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Gender",nullable = false)
	private Gender gender;
	
	
	@Column(name="Age",nullable = false)
	private Integer age;
	
	@Column(name="Address",nullable = false)
	private String address; 
	
	
	 @Override
	    public String toString() {
	        return "Patient{" +
	                "patientId='" + patientId + '\'' +
	                ", name='" + name + '\'' +
	                ", gender=" + gender +
	                ", age=" + age +
	                ", address='" + address + '\'' +
	                '}';
	    }
}
