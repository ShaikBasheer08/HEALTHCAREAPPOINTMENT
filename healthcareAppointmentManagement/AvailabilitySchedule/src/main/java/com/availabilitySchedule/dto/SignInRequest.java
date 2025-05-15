package com.availabilitySchedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
	private String Identifier;
	private String Password;
	private Role role;
}