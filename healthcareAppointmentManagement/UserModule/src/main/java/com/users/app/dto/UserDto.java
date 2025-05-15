package com.users.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.users.app.enums.Role;
import com.users.app.model.Doctor;
import com.users.app.model.Patient;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class UserDto {
    private Long userId;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    private Doctor doctor;
    private Patient patient;

    @NotNull(message = "Role cannot be null")
    private Role role;
}