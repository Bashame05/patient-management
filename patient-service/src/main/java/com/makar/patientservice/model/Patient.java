package com.makar.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String patientName;

    @NotBlank
    @Email
    @Column(unique = true)
    private String patientEmail;

    @NotBlank
    private String patientAddress;

    @NotNull
    private LocalDate patientDateOfBirth;

    @NotNull
    private LocalDate patientRegisterDate;
}
