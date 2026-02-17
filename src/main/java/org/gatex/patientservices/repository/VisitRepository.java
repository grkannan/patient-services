package org.gatex.patientservices.repository;

import org.gatex.patientservices.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {}
