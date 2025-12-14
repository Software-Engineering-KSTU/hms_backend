package org.example.backendjava.booking_to_doctore_service.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequestDto {
    private Long doctorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    private String symptomsDescribedByPatient;
    private String selfTreatmentMethodsTaken;
}