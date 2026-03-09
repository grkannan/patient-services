package org.gatex.patientservices.service;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.dto.DoctorAppointmentDTO;
import org.gatex.patientservices.entity.Appointment;
import org.gatex.patientservices.entity.AppointmentStatus;
import org.gatex.patientservices.entity.*;
import org.gatex.patientservices.repository.*;
import org.gatex.patientservices.repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;

    public Appointment create(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        return repository.save(appointment);
    }

    public Appointment completeAppointment(Long id) {
        Appointment appointment = repository.findById(id).orElseThrow();
        appointment.setStatus(AppointmentStatus.COMPLETED);
        return repository.save(appointment);
    }

    public List<DoctorAppointmentDTO> getAppointmentsByDoctor(Long doctorId) {

        List<Appointment> appointments = repository.findByDoctorId(doctorId);

        return appointments.stream().map(a -> {

            Patient patient = patientRepository
                    .findById(a.getPatientId())
                    .orElseThrow();

            Visit visit = visitRepository.findAll().stream()
                .filter(v -> v.getPatientId().equals(a.getPatientId()) &&
                            v.getDoctorId().equals(a.getDoctorId()))
                .findFirst()
                .orElse(null);            
            Long visitId = visit != null ? visit.getId() : null;

            return new DoctorAppointmentDTO(
                     a.getId(),
                    visitId,
                    patient.getFullName(),
                    a.getAppointmentDate().toString(),
                    a.getReason(),
                    a.getStatus().name()
            );

        }).toList();
    }
}
