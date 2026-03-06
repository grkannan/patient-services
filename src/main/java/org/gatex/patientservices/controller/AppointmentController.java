package org.gatex.patientservices.controller;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.dto.DoctorAppointmentDTO;
import org.gatex.patientservices.entity.Appointment;
import org.gatex.patientservices.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public Appointment create(@RequestBody Appointment appointment) {
        return service.create(appointment);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<DoctorAppointmentDTO> getAppointmentsByDoctor(@PathVariable Long doctorId) {

        return service.getAppointmentsByDoctor(doctorId);
    }

    @PutMapping("/{id}/complete")
    public Appointment completeAppointment(@PathVariable Long id) {
        return service.completeAppointment(id);
    }
}