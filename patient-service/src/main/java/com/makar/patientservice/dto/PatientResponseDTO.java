package com.makar.patientservice.dto;

public record PatientResponseDTO(
        String id,
        String patientName,
        String patientEmail,
        String patientAddress,
        String patientDateOfBirth) {}
