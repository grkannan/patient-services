package org.gatex.patientservices.service;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.entity.Appointment;
import org.gatex.patientservices.entity.AppointmentStatus;
import org.gatex.patientservices.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;

    public Appointment create(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        return repository.save(appointment);
    }
}