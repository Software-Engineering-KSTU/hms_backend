package org.example.backendjava.auth_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO для ответа при запросе на сброс пароля.
 * Содержит токен, который пользователь должен использовать для сброса.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponseDto {

    private String token;
    private String message;
    private LocalDateTime expiryDate;
}