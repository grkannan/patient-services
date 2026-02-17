package org.gatex.patientservices.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class PayBillRequest {

    @NotBlank
    private String paymentMethod;
}