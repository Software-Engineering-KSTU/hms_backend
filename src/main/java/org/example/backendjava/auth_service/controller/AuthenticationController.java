package org.example.backendjava.auth_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.model.dto.*;
import org.example.backendjava.auth_service.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-контроллер, отвечающий за аутентификацию и регистрацию пользователей.
 * <p>
 * Содержит эндпоинты для регистрации, логина и обновления токенов.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Регистрирует нового пользователя.
     *
     * @param request объект {@link UserRequestDto}, содержащий данные пользователя (логин, пароль и т.д.)
     * @return объект {@link AuthResponse} с JWT-токеном и информацией о пользователе
     */
    @PostMapping("/patient-register")
    public ResponseEntity<AuthResponse> patientRegistration(@Valid @RequestBody PatientRequestDto request) {
        return ResponseEntity.ok(authenticationService.patientRegister(request));
    }

    @PostMapping("/doctor-register")
    public ResponseEntity<AuthResponse> doctorRegistration(@Valid @RequestBody DoctorRequestDto request) {
        return ResponseEntity.ok(authenticationService.doctorRegister(request));
    }

    /**
     * Выполняет аутентификацию пользователя.
     *
     * @param request объект {@link UserRequestDto}, содержащий логин и пароль
     * @return объект {@link AuthResponse} с JWT-токеном и информацией о пользователе
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    /**
     * Обновляет JWT-токен пользователя.
     *
     * @param request HTTP-запрос с текущим токеном
     * @return объект {@link AuthResponse} с новым токеном
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
        return authenticationService.refresh(request);
    }
}
