package org.gatex.patientservices.controller;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.entity.Appointment;
import org.gatex.patientservices.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public Appointment create(@RequestBody Appointment appointment) {
        return service.create(appointment);
    }
}