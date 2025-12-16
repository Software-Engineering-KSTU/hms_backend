package org.example.backendjava.auth_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO для ответа с профилем врача.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileResponseDto {

    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String specialization;
    private String phoneNumber;
    private LocalDate birthDate;
}