package org.gatex.patientservices.dto;

import lombok.*;


import java.time.LocalDateTime;

@Data
public class VisitRequest {
    private long patientId;
    private long doctorId;
    private LocalDateTime visitDate;
}
