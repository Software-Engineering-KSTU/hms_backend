package org.example.backendjava.autth_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.autth_service.dto.AuthResponse;
import org.example.backendjava.autth_service.dto.TokenResponseDto;
import org.example.backendjava.autth_service.dto.UserRequestDto;
import org.example.backendjava.autth_service.service.AuthenticationService;
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
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Регистрирует нового пользователя.
     *
     * @param request объект {@link UserRequestDto}, содержащий данные пользователя (логин, пароль и т.д.)
     * @return объект {@link AuthResponse} с JWT-токеном и информацией о пользователе
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * Выполняет аутентификацию пользователя.
     *
     * @param request объект {@link UserRequestDto}, содержащий логин и пароль
     * @return объект {@link AuthResponse} с JWT-токеном и информацией о пользователе
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    /**
     * Обновляет JWT-токен пользователя.
     *
     * @param request HTTP-запрос с текущим токеном
     * @return объект {@link TokenResponseDto} с новым токеном
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponseDto> refreshToken(HttpServletRequest request) {
        return authenticationService.refresh(request);
    }
}
