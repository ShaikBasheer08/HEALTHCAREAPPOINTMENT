/**
 * Interface for communicating with user module to fetch doctor data
 * 

 */
package com.availabilitySchedule.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import com.availabilitySchedule.dto.AvailabilityDTO;
import com.availabilitySchedule.dto.SignInRequest;  // ✅ Import the correct DTO

@FeignClient(name = "USER-SERVICE")
public interface DoctorFeignClient {

    @PostMapping("/api/doctor/login")
    Boolean authenticateDoctor(@RequestBody SignInRequest signInRequest); // ✅ Changed LoginDto to SignInRequest

    @GetMapping("/api/doctor/{doctorId}/availability")
    List<AvailabilityDTO> getDoctorAvailability(@PathVariable Long doctorId);

    @PostMapping("/api/doctor/{doctorId}/set-availability")
    void setDoctorAvailability(@PathVariable Long doctorId, @RequestBody List<AvailabilityDTO> availabilityList);
}

