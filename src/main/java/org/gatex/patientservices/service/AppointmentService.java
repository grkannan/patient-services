package org.gatex.patientservices.service;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.dto.DoctorAppointmentDTO;
import org.gatex.patientservices.entity.Appointment;
import org.gatex.patientservices.entity.AppointmentStatus;
import org.gatex.patientservices.entity.Patient;
import org.gatex.patientservices.repository.AppointmentRepository;
import org.gatex.patientservices.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final PatientRepository patientRepository;

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

            return new DoctorAppointmentDTO(
                    a.getId(),
                    patient.getFullName(),
                    a.getAppointmentDate().toString(),
                    a.getReason(),
                    a.getStatus().name()
            );

        }).toList();
    }
}