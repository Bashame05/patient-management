package com.makar.patientservice.mapper;

import com.makar.patientservice.dto.PatientRequestDTO;
import com.makar.patientservice.model.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Function;

@Component
public class PatientRequestDTOMapper implements Function<PatientRequestDTO, Patient> {
    @Override
    public Patient apply(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setPatientName(patientRequestDTO.patientName());
        patient.setPatientEmail(patientRequestDTO.patientEmail());
        patient.setPatientAddress(patientRequestDTO.patientAddress());
        patient.setPatientDateOfBirth(LocalDate.parse(patientRequestDTO.patientDateOfBirth()));
        patient.setPatientRegisterDate(LocalDate.parse(patientRequestDTO.patientRegisterDate()));
        return patient;
    }
}
