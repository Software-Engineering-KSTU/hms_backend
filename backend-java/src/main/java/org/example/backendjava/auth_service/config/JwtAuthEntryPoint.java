package org.example.backendjava.auth_service.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Обработчик ошибок, который срабатывает, когда неаутентифицированный
 * пользователь пытается получить доступ к защищенному ресурсу.
 *
 * <p>КРИТИЧНО для CORS: Добавляет заголовки CORS к ответу 401/403,
 * чтобы браузер не блокировал его как сетевую ошибку.</p>
 */
@Component
@Slf4j
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        log.error("Unauthorized error: {}", authException.getMessage());

        // 1. Установка заголовков CORS
        // Это гарантирует, что даже при ошибке 401/403 браузер получит необходимые CORS-разрешения.
        // Вы можете заменить "*" на конкретный домен Flutter-приложения, если он известен.
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        response.setHeader("Access-Control-Max-Age", "3600");

        // 2. Установка статуса и типа содержимого
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

        // 3. Отправка JSON-сообщения об ошибке
        response.getWriter().write(
                "{\"status\": 401, \"error\": \"Unauthorized\", \"message\": \"Authentication failed: Invalid or missing token.\"}"
        );
    }
}
