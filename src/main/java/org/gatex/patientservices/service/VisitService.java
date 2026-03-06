package org.gatex.patientservices.service;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.entity.Visit;
import org.gatex.patientservices.entity.VisitStatus;
import org.gatex.patientservices.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;

    public Visit createVisit(Long patientId, Long doctorId) {
        Visit visit = Visit.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .status(VisitStatus.valueOf("PENDING"))
                .createdAt(LocalDateTime.now())
                .visitDate(LocalDateTime.now())
                .build();

        return visitRepository.save(visit);
    }

    public List<Visit> getDoctorVisits(Long doctorId) {
        return visitRepository.findByDoctorIdAndStatus(doctorId, VisitStatus.valueOf("PENDING"));
    }

    public Visit consult(Long id, String diagnosis, String prescription) {
        Visit visit = visitRepository.findById(id).orElseThrow();

        visit.setDiagnosis(diagnosis);
        visit.setPrescription(prescription);
        visit.setStatus(VisitStatus.valueOf("CONSULTING"));

        return visitRepository.save(visit);
    }

    public Visit completeVisit(Long id) {
        Visit visit = visitRepository.findById(id).orElseThrow();

        visit.setStatus(VisitStatus.valueOf("COMPLETED"));
        visit.setCompletedAt(LocalDateTime.now());

        return visitRepository.save(visit);
    }

    public Long countVisits() {
        return visitRepository.count();
    }
}