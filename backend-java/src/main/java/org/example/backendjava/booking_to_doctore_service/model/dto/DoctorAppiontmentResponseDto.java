package org.example.backendjava.booking_to_doctore_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backendjava.booking_to_doctore_service.model.entity.AppointmentStatus;

import java.time.LocalDateTime;

/**
 * DTO для возврата информации о записи врачу.
 * Содержит всю необходимую информацию о пациенте и его состоянии.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorAppiontmentResponseDto {

    /**
     * ID записи.
     */
    private Long id;
    /**
     * Информация о пациенте.
     */
    private PatientResponseDto patient;

    /**
     * Дата и время приема.
     */
    private LocalDateTime dateTime;

    /**
     * Статус приема (запланирован, в процессе и т.д.).
     */
    private AppointmentStatus status;

    /**
     * Симптомы, описанные пациентом.
     */
    private String symptomsDescribedByPatient;

    /**
     * Методы самолечения, которые применял пациент.
     */
    private String selfTreatmentMethodsTaken;
}