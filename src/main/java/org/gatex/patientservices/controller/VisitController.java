package org.gatex.patientservices.controller;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.dto.*;
import org.gatex.patientservices.entity.Visit;
import org.gatex.patientservices.service.VisitService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTION')")
    public Visit createVisit(@RequestBody VisitRequest request) {
        return visitService.createVisit(request.getPatientId(), request.getDoctorId());
    }

    @PutMapping("/{id}/consult")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Visit consult(@PathVariable Long id,
                         @RequestBody ConsultRequest request) {
        return visitService.consult(id,
                request.getDiagnosis(),
                request.getPrescription());
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Visit completeVisit(@PathVariable Long id) {
        return visitService.completeVisit(id);
    }
}

