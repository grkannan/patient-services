package org.gatex.patientservices.repository;

import org.gatex.patientservices.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {}
