package org.example.backendjava.booking_to_doctore_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.model.entity.Patient;

import java.time.LocalDateTime;

/**
 * Сущность записи к врачу.
 * Связывает пациента, врача и время приема.
 */
@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Врач, к которому записан пациент.
     */
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    /**
     * Пациент, который записался.
     */
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime dateTime;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String symptoms;


    @Column(columnDefinition = "TEXT")
    private String treatmentMethods;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "current_patient_status_id")
    private CurrentPatientStatus currentPatientStatus;
}
