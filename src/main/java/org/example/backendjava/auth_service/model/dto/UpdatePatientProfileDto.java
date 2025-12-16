package org.example.backendjava.auth_service.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO для обновления профиля пациента.
 */
@Data
public class UpdatePatientProfileDto {

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    private String address;

    private LocalDate dateOfBirth;
}
