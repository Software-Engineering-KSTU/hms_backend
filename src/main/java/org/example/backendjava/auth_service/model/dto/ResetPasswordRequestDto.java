package org.example.backendjava.auth_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для подтверждения сброса пароля.
 */
@Data
public class ResetPasswordRequestDto {

    @NotBlank(message = "Token cannot be empty")
    private String token;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;
}