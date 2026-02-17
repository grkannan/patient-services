package org.gatex.patientservices.service;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.entity.*;
import org.gatex.patientservices.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillRepository billRepository;

    @Transactional
    public Bill generateBill(Visit visit) {

        Bill bill = Bill.builder()
                .patientId(visit.getPatientId())
                .visitId(visit.getId())
                .consultationFee(500.0)
                .paid(false)
                .build();

        bill.setInvoiceNumber(generateInvoiceNumber());

        return billRepository.save(bill);
    }

    @Transactional
    public Bill addItem(Long billId, String description, Double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        Bill bill = getBill(billId);

        if (bill.getPaid()) {
            throw new RuntimeException("Cannot modify paid bill");
        }

        BillItem item = BillItem.builder()
                .description(description)
                .amount(amount)
                .bill(bill)
                .build();

        bill.getItems().add(item);

        return billRepository.save(bill);
    }

    @Transactional
    public Bill markAsPaid(Long billId, String method) {

        Bill bill = getBill(billId);

        if (bill.getPaid()) {
            throw new RuntimeException("Bill already paid");
        }

        bill.setPaid(true);
        bill.setPaidAt(LocalDateTime.now());
        bill.setPaymentMethod(method);

        return billRepository.save(bill);
    }

    public Bill getBill(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    private String generateInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}