package org.gatex.patientservices.repository;

import org.gatex.patientservices.entity.Visit;
import org.gatex.patientservices.entity.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByDoctorIdAndStatus(Long doctorId, VisitStatus status);
    List<Visit> findByDoctorId(Long doctorId);
    long count();
}