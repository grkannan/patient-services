package org.gatex.patientservices.service;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.dto.*;
import org.gatex.patientservices.entity.Visit;
import org.gatex.patientservices.entity.VisitStatus;
import org.gatex.patientservices.repository.PatientRepository;
import org.gatex.patientservices.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final BillingService billingService;

    // Create visit
    public Visit createVisit(Long patientId, Long doctorId) {
        Visit visit = Visit.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .status(VisitStatus.PENDING)
                .visitDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        return visitRepository.save(visit);
    }

    // Fetch doctor's pending visits with patient name
    public List<DoctorVisitDTO> getDoctorVisits(Long doctorId) {
        List<Visit> visits = visitRepository.findByDoctorIdAndStatus(doctorId, VisitStatus.PENDING);
        return visits.stream().map(v -> {
            String fullName = patientRepository.findById(v.getPatientId())
                    .map(p -> p.getFullName())
                    .orElse("Unknown Patient");
            
            return new DoctorVisitDTO(
                    v.getId(),
                    v.getPatientId(),
                    fullName,
                    v.getVisitDate(),
                    v.getStatus().name() // convert enum to string
            );
        }).collect(Collectors.toList());
    }

    // Finalize consultation & complete visit
    public Visit finalizeVisit(Long visitId, String diagnosis, String prescription) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        visit.setDiagnosis(diagnosis);
        visit.setPrescription(prescription);
        visit.setStatus(VisitStatus.COMPLETED);
        visit.setCompletedAt(LocalDateTime.now());
        visitRepository.save(visit);
        billingService.generateBill(visit); // Generate bill after completing visit
        return visit;
    }

    public Long countVisits() {
        return visitRepository.count();
    }
}