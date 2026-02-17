package org.gatex.patientservices.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class AddBillItemRequest {

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Double amount;
}