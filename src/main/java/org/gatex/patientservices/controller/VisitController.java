package org.gatex.patientservices.controller;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.dto.ConsultRequest;
import org.gatex.patientservices.dto.VisitRequest;
import org.gatex.patientservices.entity.Visit;
import org.gatex.patientservices.service.VisitService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    // 🔥 Reception creates visit
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTION')")
    public Visit createVisit(@RequestBody VisitRequest request) {
        return visitService.createVisit(
                request.getPatientId(),
                request.getDoctorId()
        );
    }

    // 🔥 Doctor gets ONLY his own visits (SAFE)
    @GetMapping("/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public List<Visit> getDoctorVisits(Authentication authentication) {

        // JWT subject = userId
        Long doctorId = Long.parseLong(authentication.getName());

        return visitService.getDoctorVisits(doctorId);
    }

    // 🔥 Doctor consult
    @PutMapping("/{id}/consult")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Visit consult(@PathVariable Long id,
                         @RequestBody ConsultRequest request) {

        return visitService.consult(
                id,
                request.getDiagnosis(),
                request.getPrescription()
        );
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Long countVisits() {
        return visitService.countVisits();
    }

    // 🔥 Complete visit
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Visit completeVisit(@PathVariable Long id) {
        return visitService.completeVisit(id);
    }
}