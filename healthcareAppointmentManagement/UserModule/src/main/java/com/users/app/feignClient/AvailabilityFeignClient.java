package com.users.app.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.users.app.enums.Specialization;

@FeignClient(name = "AVAILABILITY-SERVICE")
public interface AvailabilityFeignClient {

    // ✅ Corrected Method to Create Doctor Availability
    @PostMapping("/availability/create/doctor/{doctorId}/{doctorName}/{specialization}")
    void createAvailabilityForDoctorId(@PathVariable Long doctorId,
                                       @PathVariable String doctorName,
                                       @PathVariable Specialization specialization);

    // ✅ Doctors Manually Set Availability
    @PostMapping("/availability/doctor/{doctorId}/set-availability")
    void setDoctorAvailability(@PathVariable Long doctorId, @RequestBody Object availabilityList);

    // ✅ Fetch a Doctor's Availability for Patients
    @GetMapping("/availability/doctor/{doctorId}")
    Object getDoctorAvailability(@PathVariable Long doctorId);
}
