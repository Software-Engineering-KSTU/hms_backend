package org.example.backendjava.doctor_cabinet_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.model.entity.Patient;

import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

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
