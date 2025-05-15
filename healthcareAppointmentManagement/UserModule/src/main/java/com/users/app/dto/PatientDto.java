package com.users.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.users.app.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
	private Long patient_id;
	private String name;
	private Gender gender;
	@Email(message="Email entered is Invalid")
	private String email;
	@Size(min=8,message="password should be atleast 8 characters")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private Integer age;
	private String address;
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
	private String phoneNumber;
}
