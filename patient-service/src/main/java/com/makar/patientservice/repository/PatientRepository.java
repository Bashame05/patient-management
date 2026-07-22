package com.makar.patientservice.repository;

import com.makar.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByPatientEmail(String patientEmail);

    boolean existsById(UUID id);

    boolean existsByPatientEmailAndIdNot(String patientEmail, UUID id);
}
