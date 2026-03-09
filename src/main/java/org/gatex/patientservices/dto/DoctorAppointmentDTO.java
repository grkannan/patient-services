package org.gatex.patientservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorAppointmentDTO {

    private Long id;
    private Long visitId;
    private String fullName;
    private String appointmentDate;
    private String reason;
    private String status;
}
