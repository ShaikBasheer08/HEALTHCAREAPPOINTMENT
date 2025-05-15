 package com.healthcare.appointment.feignClients;
 
 import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthcare.appointment.dto.AppointmentDto;
import com.healthcare.appointment.dto.Response;
import com.healthcare.appointment.model.Appointment;
/**
* Notification Service Feign Client.
* 
* @Author Sanjay R
* @Since 2025-03-18
*/
 @FeignClient(name="NOTIFICATION-SERVICE",configuration= FeignClientConfiguration.class)
public interface NotificationFeignClient {
	 
	 
	 
	 @PostMapping("/notifications/create")
	  public ResponseEntity<String> createNotification(@RequestBody AppointmentDto appointmentDto);
	 
	 @PutMapping("/notifications/onCompletion")
	  public void onCompletion(@RequestBody AppointmentDto appointment);
	 

	 @PutMapping("/notifications/onUpdate")
	 public void onUpdate(@RequestBody Appointment appointment);
	 
	 
	 
}