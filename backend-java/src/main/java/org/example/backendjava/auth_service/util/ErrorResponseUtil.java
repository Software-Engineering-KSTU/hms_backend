package org.example.backendjava.auth_service.util;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Утилитный класс с методом
 * для создания ответа ошибки.
 */
public class ErrorResponseUtil {

    private ErrorResponseUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Создает стандартный JSON-ответ с информацией об ошибке.
     *
     * @param status  HTTP статус ошибки
     * @param message Сообщение об ошибке
     * @return Map с деталями ошибки
     */

    public static Map<String, Object> createErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return error;
    }
}

