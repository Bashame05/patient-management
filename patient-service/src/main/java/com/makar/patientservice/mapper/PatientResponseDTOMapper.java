package com.makar.patientservice.mapper;


import com.makar.patientservice.dto.PatientResponseDTO;
import com.makar.patientservice.model.Patient;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PatientResponseDTOMapper implements Function<Patient, PatientResponseDTO> {
    @Override
    public PatientResponseDTO apply(Patient patient) {
        return new PatientResponseDTO(
                patient.getId().toString(),
                patient.getPatientName(),
                patient.getPatientEmail(),
                patient.getPatientAddress(),
                patient.getPatientDateOfBirth().toString()
        );
    }
}
