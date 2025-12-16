package org.example.backendjava.auth_service.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO для обновления профиля врача.
 */
@Data
public class UpdateDoctorProfileDto {

    private String specialization;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    private LocalDate dateOfBirth;
}