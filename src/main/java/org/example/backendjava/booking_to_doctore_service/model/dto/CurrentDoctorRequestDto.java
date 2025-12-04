package org.example.backendjava.booking_to_doctore_service.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CurrentDoctorRequestDto {
    private LocalDate date;
    private Long doctorId;
}
