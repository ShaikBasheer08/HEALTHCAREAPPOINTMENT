package com.users.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.users.app.enums.Specialization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
	private Long doctor_id;
	private String name;
	@Email(message = "Email entered is Invalid")
	private String email;
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
	private String phoneNumber;
    
	private Specialization specialization;
	@Size(min = 8 , message = "password should be atleast 8 characters")
	 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
}
