package com.makar.patientservice;


import com.makar.patientservice.dto.PatientRequestDTO;
import com.makar.patientservice.dto.PatientResponseDTO;
import com.makar.patientservice.service.PatientService;
import com.makar.patientservice.validators.CreatePatientValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient" , description = "API for managing patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponseDTO> addPatient(@Validated({Default.class, CreatePatientValidationGroup.class})
                                                         @RequestBody PatientRequestDTO addedPatient) {
        return ResponseEntity.ok(patientService.addPatient(addedPatient));
    }

    @PutMapping("/{patientId}")
    @Operation(summary = "Update patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID patientId,
                                                            @Validated({Default.class})
                                                            @RequestBody PatientRequestDTO patientRequestDTO) {

        return ResponseEntity.ok()
                .body(patientService.updatePatient(patientId, patientRequestDTO));
    }

    @DeleteMapping("/{patientId}")
    @Operation(summary = "Delete patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID patientId){
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}
