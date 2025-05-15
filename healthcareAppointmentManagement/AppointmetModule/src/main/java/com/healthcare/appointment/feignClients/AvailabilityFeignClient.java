package com.healthcare.appointment.feignClients;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.healthcare.appointment.dto.AvailabilityDto;

/**
 * Availability Service Feign Client.
 */
@FeignClient(name = "AVAILABILITY-SERVICE") // Removed hardcoded URL for Eureka Discovery
public interface AvailabilityFeignClient {

    @PutMapping("/availability/update/{availabilityId}/reschedule/{newAvailabilityId}")
    ResponseEntity<String> updateAvailability(
        @PathVariable Long availabilityId, 
        @PathVariable Long newAvailabilityId
    );

    @GetMapping("/availability/doctor/{doctorId}/date/{date}")
    List<AvailabilityDto> getAvailabilityByDoctorIdAndDate(
        @PathVariable Long doctorId, 
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    );

    @GetMapping("/availability/specialization/{specialization}/date/{date}")
    List<AvailabilityDto> getAvailabilityBySpecializationAndDate(
        @PathVariable("specialization") String specialization, // ✅ Handles capitalization correctly
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    );

    @PutMapping("/availability/book/{availabilityId}")
    ResponseEntity<AvailabilityDto> bookTimeSlot(@PathVariable Long availabilityId);

    @PutMapping("/availability/cancel/{availabilityId}")
    ResponseEntity<String> cancelAvailability(@PathVariable Long availabilityId);

    @GetMapping("/availability/{availabilityId}")
    ResponseEntity<AvailabilityDto> viewById(@PathVariable Long availabilityId);

    @GetMapping("/availability/doctor/{doctorId}/date-range")
    List<AvailabilityDto> getAvailabilityByDoctorIdAndDateRange(
        @PathVariable Long doctorId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @GetMapping("/availability/specialization/{specialization}/date-range")
    List<AvailabilityDto> getAvailabilityBySpecializationAndDateRange(
        @PathVariable("specialization") String specialization, // ✅ Uses string for enum handling
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );
}
