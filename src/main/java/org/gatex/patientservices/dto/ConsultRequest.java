package org.gatex.patientservices.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultRequest {
    private String diagnosis;
    private String prescription;
}