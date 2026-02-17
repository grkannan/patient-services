package org.gatex.patientservices.dto;

import lombok.Data;

@Data
public class ConsultRequest {
    private String diagnosis;
    private String prescription;
}
