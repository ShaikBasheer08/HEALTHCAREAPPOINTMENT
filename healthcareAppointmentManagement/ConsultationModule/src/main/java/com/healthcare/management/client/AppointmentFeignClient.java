package com.healthcare.management.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.healthcare.management.dto.AppointmentDto;
import com.healthcare.management.config.FeignClientConfiguration; // <--- Import your new configuration class

/**
 * Feign Client to communicate with the Appointment Microservice.
 */
@FeignClient(name = "APPOINTMENT-SERVICE", configuration = FeignClientConfiguration.class) // <--- Add this
public interface AppointmentFeignClient {

    @GetMapping("/appointments/{appointmentId}")
    AppointmentDto getAppointmentById(@PathVariable Long appointmentId);
}