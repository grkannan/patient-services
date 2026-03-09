package org.gatex.patientservices.controller;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.entity.*;
import org.gatex.patientservices.repository.VisitRepository;
import org.gatex.patientservices.service.BillingService;
import org.gatex.patientservices.service.VisitService;
import org.gatex.patientservices.dto.DoctorVisitDTO;
import org.gatex.patientservices.dto.VisitRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final VisitRepository visitRepository;
    private final BillingService billingService; // ✅ Added billing service

    // Reception creates visit
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTION')")
    public Visit createVisit(@RequestBody VisitRequest request) {
        return visitService.createVisit(request.getPatientId(), request.getDoctorId());
    }

    // Doctor gets only pending visits
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public List<DoctorVisitDTO> getDoctorVisits(@PathVariable Long doctorId) {
        return visitService.getDoctorVisits(doctorId);
    }

    // 🔹 Complete visit & generate bill
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeVisit(
        @PathVariable Long id,
        @RequestParam String diagnosis,
        @RequestParam String prescription
    ) {
        Visit visit = visitService.finalizeVisit(id, diagnosis, prescription);
        return ResponseEntity.ok(visit);
    }

    // Admin stats
    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Long countVisits() {
        return visitService.countVisits();
    }
}
