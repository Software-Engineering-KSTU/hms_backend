package org.example.backendjava.booking_to_doctore_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
}
