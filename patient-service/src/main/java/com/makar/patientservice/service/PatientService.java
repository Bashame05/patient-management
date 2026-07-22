package com.makar.patientservice.service;


import com.makar.patientservice.dto.PatientRequestDTO;
import com.makar.patientservice.dto.PatientResponseDTO;
import com.makar.patientservice.exception.EmailAlreadyExistsException;
import com.makar.patientservice.exception.PatientNotFoundException;
import com.makar.patientservice.grpc.BillingServiceGrpcClient;
import com.makar.patientservice.kafka.KafkaProducer;
import com.makar.patientservice.mapper.PatientRequestDTOMapper;
import com.makar.patientservice.mapper.PatientResponseDTOMapper;
import com.makar.patientservice.model.Patient;
import com.makar.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientResponseDTOMapper patientResponseDTOMapper;
    private final PatientRequestDTOMapper patientRequestDTOMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository,
                          PatientResponseDTOMapper patientResponseDTOMapper, PatientRequestDTOMapper patientRequestDTOMapper, BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.patientResponseDTOMapper = patientResponseDTOMapper;
        this.patientRequestDTOMapper = patientRequestDTOMapper;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientResponseDTOMapper)
                .collect(Collectors.toList());
    }

    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByPatientEmail(patientRequestDTO.patientEmail()))
            throw new EmailAlreadyExistsException("Enter a unique email for valid registration");
        Patient newPatient = patientRepository.save(patientRequestDTOMapper.apply(patientRequestDTO));
        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getPatientName(),
                newPatient.getPatientEmail());
        kafkaProducer.sendEvent(newPatient);
        return patientResponseDTOMapper.apply(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patientToUpdate = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with the given ID not found"));
        if (patientRepository.existsByPatientEmailAndIdNot(patientRequestDTO.patientEmail(), id))
            throw new EmailAlreadyExistsException("Enter a unique email to update");
        patientToUpdate.setPatientName(patientRequestDTO.patientName());
        patientToUpdate.setPatientEmail(patientRequestDTO.patientEmail());
        patientToUpdate.setPatientAddress(patientRequestDTO.patientAddress());
        patientToUpdate.setPatientDateOfBirth(LocalDate.parse(patientRequestDTO.patientDateOfBirth()));

        return patientResponseDTOMapper.apply(patientRepository.save(patientToUpdate));
    }

    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id))
            throw new PatientNotFoundException("Patient with the given ID not found");
        patientRepository.deleteById(id);
    }

}
