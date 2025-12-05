package org.example.backendjava.booking_to_doctore_service.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentRequestDto {
    private Long doctorId;

    // Поле только для Даты (Год-Месяц-День)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    // Поле только для Времени (Часы:Минуты)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;

    private String symptomsDescribedByPatient;
    private String selfTreatmentMethodsTaken;
}