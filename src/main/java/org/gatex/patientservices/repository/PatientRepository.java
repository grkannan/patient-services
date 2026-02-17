package org.gatex.patientservices.repository;

import org.gatex.patientservices.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {}
