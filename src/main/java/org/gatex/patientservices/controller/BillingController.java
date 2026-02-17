package org.gatex.patientservices.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.dto.*;
import org.gatex.patientservices.entity.Bill;
import org.gatex.patientservices.service.BillingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/{billId}/add-item")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTION')")
    public Bill addItem(@PathVariable Long billId,
                        @RequestBody @Valid AddBillItemRequest request) {
        return billingService.addItem(
                billId,
                request.getDescription(),
                request.getAmount());
    }

    @PutMapping("/{billId}/pay")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTION')")
    public Bill pay(@PathVariable Long billId,
                    @RequestBody @Valid PayBillRequest request) {
        return billingService.markAsPaid(
                billId,
                request.getPaymentMethod());
    }

    @GetMapping("/{billId}")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTION','DOCTOR')")
    public Bill getBill(@PathVariable Long billId) {
        return billingService.getBill(billId);
    }
}