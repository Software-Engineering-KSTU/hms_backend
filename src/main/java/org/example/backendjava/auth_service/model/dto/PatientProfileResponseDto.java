package org.example.backendjava.auth_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO для ответа с профилем пациента.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientProfileResponseDto {

    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
}
