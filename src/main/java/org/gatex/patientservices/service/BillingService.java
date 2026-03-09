package org.gatex.patientservices.service;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.entity.*;
import org.gatex.patientservices.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillRepository billRepository;

    @Transactional
    public Bill generateBill(Visit visit) {
        Optional<Bill> existing = billRepository.findByVisitId(visit.getId());
        if (existing.isPresent()) {
            return existing.get(); // Bill already exists for this visit
        }   
        Bill bill = new Bill();
        double consultFee = 500.0;
        double addCharege = 0.0; 
        
        bill.setVisitId(visit.getId());
        bill.setPatientId(visit.getPatientId());
        bill.setInvoiceNumber(generateInvoiceNumber());
        bill.setConsultationFee(consultFee);
        bill.setAdditionalCharges(addCharege);
        bill.setTotalAmount(consultFee + addCharege);
        bill.setPaid(false);
        bill.setCreatedAt(LocalDateTime.now());
        bill.setUpdatedAt(LocalDateTime.now());

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

    public Bill getBillByVisitId(Long visitId) {
        return billRepository.findByVisitId(visitId)
                .orElseThrow(() -> new RuntimeException("Bill not found for visit"));
    }

    private String generateInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }   
}