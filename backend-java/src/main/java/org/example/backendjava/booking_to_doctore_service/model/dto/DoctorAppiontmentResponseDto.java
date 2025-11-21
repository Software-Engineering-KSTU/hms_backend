package org.example.backendjava.booking_to_doctore_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorAppiontmentResponseDto {

    private Long id;
    private PatientResponseDto patient;
    private LocalDateTime dateTime;
    private String status;
}
