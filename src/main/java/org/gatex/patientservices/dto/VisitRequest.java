package org.gatex.patientservices.dto;

import lombok.*;

@Data
public class VisitRequest {
    private long patientId;
    private long doctorId;
}
