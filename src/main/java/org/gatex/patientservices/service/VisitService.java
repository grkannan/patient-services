package org.gatex.patientservices.service;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.entity.Visit;
import org.gatex.patientservices.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final BillingService billingService;

    @Transactional
    public Visit createVisit(Long patientId, Long doctorId) {
        Visit visit = new Visit();
        visit.setPatientId(patientId);
        visit.setDoctorId(doctorId);
        visit.setStatus("OPEN");
        visit.setCreatedAt(LocalDateTime.now());
        return visitRepository.save(visit);
    }

    @Transactional
    public Visit consult(Long id, String diagnosis, String prescription) {
        Visit visit = getVisit(id);

        if (!"OPEN".equals(visit.getStatus())) {
            throw new RuntimeException("Visit not open for consultation");
        }

        visit.setDiagnosis(diagnosis);
        visit.setPrescription(prescription);

        return visitRepository.save(visit);
    }

    @Transactional
    public Visit completeVisit(Long id) {
        Visit visit = getVisit(id);

        if (!"OPEN".equals(visit.getStatus())) {
            throw new RuntimeException("Visit already completed");
        }

        visit.setStatus("COMPLETED");
        visit.setCompletedAt(LocalDateTime.now());

        visitRepository.save(visit);

        billingService.generateBill(visit);

        return visit;
    }

    private Visit getVisit(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit not found"));
    }

}