package com.makar.patientservice.dto;

import com.makar.patientservice.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record PatientRequestDTO(
        @NotBlank(message = "Name is required for registration")
        @Size(max = 100 , message = "Name cannot exceed 100 characters")
        String patientName,

        @NotBlank(message = "Email field is required")
        @Email(message = "Email should be valid")
        String patientEmail,

        @NotBlank(message = "Address field is required")
        String patientAddress,

        @NotBlank(message = "DOB is required")
        String patientDateOfBirth,

        @NotBlank(groups = CreatePatientValidationGroup.class ,message = "Register Date is required")
        String patientRegisterDate
) {
}
