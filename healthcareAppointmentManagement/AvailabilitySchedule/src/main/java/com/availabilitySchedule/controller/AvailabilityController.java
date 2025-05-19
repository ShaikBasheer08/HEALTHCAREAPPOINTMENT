package com.availabilitySchedule.controller;

import com.availabilitySchedule.dto.AvailabilityDTO;
import com.availabilitySchedule.dto.DoctorAvailabilityRequest;
import com.availabilitySchedule.exception.AvailabilityNotFoundException;
import com.availabilitySchedule.exception.DatabaseException;
import com.availabilitySchedule.service.AvailabilityService;
import com.availabilitySchedule.dto.Response;
import com.availabilitySchedule.model.Availability;
import com.availabilitySchedule.model.Specialization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/availability")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/create/doctor")
    public ResponseEntity<String> createAvailabilityForDoctor(@RequestBody DoctorAvailabilityRequest request) {
        log.info("Creating manual availabilities for Doctor ID: {}", request.getDoctorId());
        try {
            availabilityService.createAvailabilityForDoctorId(
                    request.getDoctorId(), request.getDoctorName(), request.getSpecialization(), request.getAvailability()
            );
            return ResponseEntity.ok("Availability slots added successfully.");
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create availability: " + e.getMessage()); //handles the DatabaseException
        }
    }

    @GetMapping("/{availabilityId}")
    public ResponseEntity<AvailabilityDTO> getAvailabilityById(@PathVariable Long availabilityId) {
        AvailabilityDTO availability = availabilityService.getAvailabilityById(availabilityId);
        return ResponseEntity.ok(availability); // Removed the null check, the service throws exception.
    }

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilityByDoctorIdAndDate(
            @PathVariable Long doctorId, @PathVariable LocalDate date) {
        List<AvailabilityDTO> availabilityDtos = availabilityService.getAvailabilityByDoctorIdAndDate(doctorId, date)
                .stream()
                .map(AvailabilityDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(availabilityDtos);
    }

    // ✅ Patients fetch doctor availability
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AvailabilityDTO>> getDoctorAvailability(@PathVariable Long doctorId) {
        List<AvailabilityDTO> availabilityDtos = availabilityService.getDoctorAvailability(doctorId)
                .stream()
                .map(AvailabilityDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(availabilityDtos);
    }

    // ✅ Patients fetch availability by specialization & date
    @GetMapping("/specialization/{specialization}/date/{date}")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilityBySpecializationAndDate(
            @PathVariable String specialization, @PathVariable LocalDate date) {

        List<AvailabilityDTO> availabilityDtos = availabilityService
                .getAvailabilityBySpecializationAndDate(Specialization.valueOf(specialization), date)
                .stream()
                .map(AvailabilityDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(availabilityDtos);
    }

    @PutMapping("/book/{availabilityId}")
    public ResponseEntity<AvailabilityDTO> bookTimeSlot(@PathVariable Long availabilityId) {
        AvailabilityDTO updatedAvailability = availabilityService.bookTimeSlot(availabilityId);
        return ResponseEntity.ok(updatedAvailability);
    }

    // ✅ Patients cancel a booked slot
    @PutMapping("/cancel/{availabilityId}")
    public ResponseEntity<AvailabilityDTO> cancelAvailability(@PathVariable Long availabilityId) {
        AvailabilityDTO canceledAvailability = availabilityService.cancelAvailabilityStatus(availabilityId);
        return ResponseEntity.ok(canceledAvailability);
    }

    @GetMapping("/doctors")
    public ResponseEntity<Response<List<?>>> viewAllAvailabilities() {
        List<Availability> availabilities = availabilityService.viewAllAvailabilities();
        List<AvailabilityDTO> availabilityDtos = new ArrayList<AvailabilityDTO>();
        for (Availability availability : availabilities) {
            AvailabilityDTO availabilityDto = AvailabilityDTO.fromEntity(availability);
            availabilityDtos.add(availabilityDto);
        }
        Response<List<?>> response = new Response<>(true, HttpStatus.OK, availabilityDtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ✅ Delete availability
    @DeleteMapping("/delete/{availabilityId}")
    public ResponseEntity<String> deleteAvailability(@PathVariable Long availabilityId) {
        availabilityService.deleteAvailability(availabilityId);
        return ResponseEntity.ok("Availability deleted successfully.");
    }

    @PutMapping("/update/{availabilityId}/reschedule/{newAvailabilityId}")
    public ResponseEntity<String> updateAvailability(
            @PathVariable Long availabilityId,
            @PathVariable Long newAvailabilityId) {
        // Implement your logic here to update the availability
        // You'll need to call the service layer to handle the actual update
        availabilityService.updateAvailability(availabilityId, newAvailabilityId); // Added service layer call
        return ResponseEntity.ok("Availability updated successfully.");
    }

    @GetMapping("/doctor/{doctorId}/date-range")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilityByDoctorIdAndDateRange(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AvailabilityDTO> availabilityDtos = availabilityService.getAvailabilityByDoctorIdAndDateRange(doctorId, startDate, endDate);
        return ResponseEntity.ok(availabilityDtos);
    }

    @GetMapping("/specialization/{specialization}/date-range")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilityBySpecializationAndDateRange(
            @PathVariable("specialization") String specialization,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AvailabilityDTO> availabilityDtos = availabilityService.getAvailabilityBySpecializationAndDateRange(specialization, startDate, endDate);
        return ResponseEntity.ok(availabilityDtos);
    }
}