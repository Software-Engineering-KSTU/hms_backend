package org.example.backendjava.booking_to_doctore_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Хранения текущего статуса пациента.
 * Информация о симптомах и предпринятых мерах самолечения.
 */
@Entity
@Table(name = "current_patient_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPatientStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Симптомы, описанные пациентом.
     * Например: "Высокая температура, кашель, головная боль"
     */
    @Column(columnDefinition = "TEXT")
    private String symptomsDescribedByPatient;

    /**
     * Методы самолечения, которые применял пациент.
     * Например: "Принимал парацетамол, пил много воды, соблюдал постельный режим"
     */
    @Column(columnDefinition = "TEXT")
    private String selfTreatmentMethodsTaken;

    /**
     * Текущий статус записи к врачу.
     */
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}