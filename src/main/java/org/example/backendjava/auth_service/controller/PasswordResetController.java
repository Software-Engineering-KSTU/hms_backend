package org.example.backendjava.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.model.dto.ForgotPasswordRequestDto;
import org.example.backendjava.auth_service.model.dto.PasswordResetResponseDto;
import org.example.backendjava.auth_service.model.dto.ResetPasswordRequestDto;
import org.example.backendjava.auth_service.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST-контроллер для управления сбросом пароля.
 * Предоставляет эндпоинты для запроса токена и сброса пароля.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Password Reset", description = "Управление сбросом пароля")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    /**
     * Запрос на сброс пароля.
     * Генерирует токен для пользователя по email.
     *
     * @param request DTO с email пользователя
     * @return DTO с токеном и информацией о сроке действия
     */
    @PostMapping("/forgot-password")
    @Operation(
            summary = "Запрос на сброс пароля",
            description = "Генерирует токен для сброса пароля. Токен действителен 30 минут."
    )
    public ResponseEntity<PasswordResetResponseDto> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequestDto request) {

        PasswordResetResponseDto response = passwordResetService.createPasswordResetToken(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Подтверждение сброса пароля.
     * Проверяет токен и устанавливает новый пароль.
     *
     * @param request DTO с токеном и новым паролем
     * @return сообщение об успешном сбросе пароля
     */
    @PostMapping("/reset-password")
    @Operation(
            summary = "Сброс пароля",
            description = "Проверяет токен и устанавливает новый пароль. " +
                    "Все активные JWT токены пользователя будут аннулированы."
    )
    public ResponseEntity<Map<String, String>> resetPassword(
            @Valid @RequestBody ResetPasswordRequestDto request) {

        passwordResetService.resetPassword(request);

        return ResponseEntity.ok(Map.of(
                "message", "Password has been reset successfully. Please login with your new password."
        ));
    }
}