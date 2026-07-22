package com.makar.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    //allows our consumer to listen to the specified topic
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] patientEvent) {
        try {
            PatientEvent consumePatientEvent = PatientEvent.parseFrom(patientEvent);
            log.info("Received Patient Event: [PatientId = {} , PatientName = {} , PatientEmail = {}",
                    consumePatientEvent.getPatientId(), consumePatientEvent.getPatientName(), consumePatientEvent.getPatientEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing the event : {}", e.getMessage());
        }

    }
}
