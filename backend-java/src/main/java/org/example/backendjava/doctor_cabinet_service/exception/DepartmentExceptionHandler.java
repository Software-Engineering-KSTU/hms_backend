package org.example.backendjava.doctor_cabinet_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static org.example.backendjava.auth_service.util.ErrorResponseUtil.createErrorResponse;

/**
 * Глобальный обработчик исключений для операций с отделениями.
 * <p>
 * Перехватывает ошибки поиска отделений и конфликты при создании дубликатов,
 * формируя стандартизированный JSON-ответ с информацией об ошибке.
 */
@Order(3)
@ControllerAdvice
public class DepartmentExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DepartmentExceptionHandler.class);

    /**
     * Обрабатывает ошибку дублирования названия отделения.
     */
    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDepartmentAlreadyExists(DepartmentAlreadyExistsException ex) {
        log.warn("Conflict: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Обрабатывает ошибку, когда отделение не найдено.
     */
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDepartmentNotFound(DepartmentNotFoundException ex) {
        log.warn("Department not found: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}