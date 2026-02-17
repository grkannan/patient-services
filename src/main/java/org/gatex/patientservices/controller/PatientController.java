package org.gatex.patientservices.controller;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.entity.Patient;
import org.gatex.patientservices.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @PostMapping
    public Patient create(@RequestBody Patient patient) {
        return service.create(patient);
    }

    @GetMapping("/{id}")
    public Patient get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Patient> getAll() {
        return service.getAll();
    }
}