package org.gatex.patientservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DoctorVisitDTO {
    private Long visitId;
    private Long patientId;
    private String patientName;
    private LocalDateTime visitDate;
    private String reason;
    private String status; // PENDING, COMPLETED etc.
}
