package com.cts.healthcareappointment.notificationmodule.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @Column(name = "Notification_id", nullable = false, unique = true)
    private String notification_id;

    @NotNull(message = "Notification type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
    private NotificationType type;

    
   
    @Column(name = "Message")
    private String message;

   // @Column(name = "SeenStatus", nullable = false)
    //private boolean seenStatus = false;

    @NotNull(message = "Notification status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private NotificationStatus status;

   
    @Column(name = "Doctor_id", nullable = true)
    private String doctorId;

    
    @Column(name = "Patient_id", nullable = true)
    private String patientId;

    private LocalDateTime date;
    
    private String appointmentId;
  //  @ManyToOne
    //@JoinColumn(name = "user_id", referencedColumnName = "user_id")
    //private User notify;

    @PrePersist
    public void generateUUID() {
        if (this.notification_id == null || this.notification_id.isEmpty()) {
            this.notification_id = UUID.randomUUID().toString();
        }
    }
}
