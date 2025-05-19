package com.users.app.controller;
 
import java.util.Arrays;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;
 
import com.users.app.dto.ApiResponse;

import com.users.app.dto.DoctorAvailabilityDto;

import com.users.app.dto.DoctorDto;

import com.users.app.dto.PatientDto;

import com.users.app.dto.SignInRequest;

import com.users.app.enums.Specialization;

import com.users.app.exceptions.DoctorNotFoundException;

import com.users.app.exceptions.EmailAlreadyExistsException;

import com.users.app.exceptions.PhoneNumberAlreadyExistsException;

import com.users.app.exceptions.UserNotFoundException;

import com.users.app.feignClient.AvailabilityFeignClient;

import com.users.app.model.Doctor;

import com.users.app.service.DoctorService;
 
import jakarta.validation.Valid;
 
@RestController

@RequestMapping("/api/doctor")

@CrossOrigin(origins = "http://localhost:3000")

@Validated

public class DoctorController {

    @Autowired

    private DoctorService doctorService;
 
    @Autowired

    private AvailabilityFeignClient availabilityFeignClient; // ✅ Corrected Feign Client Injection
 
    // ✅ Doctor Login Endpoint

    @PostMapping("/login")

    public ResponseEntity<ApiResponse<?>> doctorLogin(@RequestBody @Valid SignInRequest signInRequest) {

        try {

            boolean isAuthenticated = doctorService.authenticateDoctor(signInRequest);

            if (isAuthenticated) {

                return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", null));

            }

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid credentials", null));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));

        }

    }
 
 
    // ✅ Doctors Submit Availability Manually via Feign Client

    @PostMapping("/{doctorId}/set-availability")

    public ResponseEntity<ApiResponse<?>> setDoctorAvailability(@PathVariable Long doctorId, @RequestBody Object availabilityList) {

        try {

            availabilityFeignClient.setDoctorAvailability(doctorId, availabilityList); // ✅ Use Feign Client Instead

            return ResponseEntity.ok(new ApiResponse<>(true, "Availability slots added successfully", null));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));

        }

    }
 
    // ✅ Fetch Doctor's Availability for Patients

    @GetMapping("/{doctorId}/availability")

    public ResponseEntity<ApiResponse<?>> getDoctorAvailability(@PathVariable Long doctorId) {

        try {

            Object availabilitySlots = availabilityFeignClient.getDoctorAvailability(doctorId); // ✅ Corrected Feign Client Call

            return ResponseEntity.ok(new ApiResponse<>(true, "Doctor Availability", availabilitySlots));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));

        }

    }
 
    // ✅ Fetch doctor details

    @GetMapping("/{id}")

    public ResponseEntity<DoctorDto> getDoctor(@PathVariable Long id) {
    	System.out.println("Inside the doctor method");

        try {

            System.out.println("Inside the doctor method");

            DoctorDto doctor = doctorService.getDoctorById(id);

            return ResponseEntity.ok(doctor); // ✅ Return DoctorDto directly

        } catch (DoctorNotFoundException e) {

            return ResponseEntity.notFound().build(); // ✅ Return 404 if doctor is not found

        }

    }
 
//    // ✅ Update doctor details

    @PatchMapping("/update")

    public ResponseEntity<ApiResponse<?>> updateDoctor(@RequestBody @Valid DoctorDto doc) {

        try {

            doctorService.updateDoctorDetails(doc);

            return ResponseEntity.ok(new ApiResponse<>(true, "Doctor Details Updated Successfully", null));

        } catch (DoctorNotFoundException | PhoneNumberAlreadyExistsException | EmailAlreadyExistsException e) {

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));

        }

    }

 
    // ✅ Search for doctors by specialization

    @GetMapping("/doctorSearch")

    public ResponseEntity<ApiResponse<?>> getDoctors(@RequestParam String specialization) {

        try {

            boolean isValidSpecialization = Arrays.stream(Specialization.values())

                    .anyMatch(s -> s.name().equalsIgnoreCase(specialization));
 
            if (!isValidSpecialization) {

                throw new IllegalArgumentException("Invalid Specialization: " + specialization);

            }
 
            Specialization specEnum = Specialization.valueOf(specialization);

            List<Doctor> doctors = doctorService.getDoctorBySpecialization(specEnum);
 
            return ResponseEntity.ok(new ApiResponse<>(true, "Doctor Details", doctors));

        } catch (DoctorNotFoundException | IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));

        }

    }
 
    // ✅ Fetch all doctors (basic details)

    @GetMapping("/alldoctors")

    public List<DoctorAvailabilityDto> getAllDoctors() {

        try {

            return doctorService.getAllDoctors();

        } catch (DoctorNotFoundException e) {

            e.printStackTrace();

            return null;

        }

    }
 
    // ✅ Fetch all doctors with full details

    @GetMapping("/alldoctorDetails")

    public ResponseEntity<ApiResponse<?>> getAllDoctorsDetails() {

        try {

            List<Doctor> doctors = doctorService.getAllDoctorsdetails();

            return ResponseEntity.ok(new ApiResponse<>(true, "Doctor Details", doctors));

        } catch (DoctorNotFoundException e) {

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));

        }

    }

}

 