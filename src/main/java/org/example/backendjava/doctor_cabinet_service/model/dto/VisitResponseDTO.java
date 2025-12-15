package org.example.backendjava.doctor_cabinet_service.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitResponseDTO {

    private Long id;

    private Long doctorId;
    private Long patientId;

    private LocalDateTime visitDate;

    private String symptoms;
    private String diagnosis;
    private String lifeAnamnesis;
    private String diseaseAnamnesis;
    private String initialExamination;
    private String stateDynamics;
    private String labResults;
    private String instrumentalResults;
    private String recommendations;

    private String medications;
    private String dosage;

    private LocalDateTime dischargeDate;
    private Integer treatmentDurationDays;
}
