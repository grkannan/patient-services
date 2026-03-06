package org.gatex.patientservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorAppointmentDTO {

    private Long id;
    private String patientName;
    private String appointmentDate;
    private String reason;
    private String status;
}