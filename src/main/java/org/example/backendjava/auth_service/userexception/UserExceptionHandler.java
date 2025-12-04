package org.example.backendjava.auth_service.userexception;

import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.example.backendjava.auth_service.util.ErrorResponseUtil.createErrorResponse;

/**
 * Глобальный обработчик исключений для операций с пользователями.
 * <p>
 * Перехватывает ошибки валидации, аутентификации, дублирования данных и другие
 * исключения, формируя стандартизированный JSON-ответ с информацией об ошибке.
 */
@Order(2)
@ControllerAdvice
public class UserExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UserExceptionHandler.class);

    /**
     * Обрабатывает ошибки валидации полей запроса.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> response = createErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed");
        response.put("fields", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Обрабатывает ошибки дублирования username или email.
     */
    @ExceptionHandler({UsernameAlreadyExistsException.class, EmailAlreadyExistsException.class})
    public ResponseEntity<Map<String, Object>> handleAlreadyExistsExceptions(RuntimeException ex) {
        log.warn("Conflict: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Обрабатывает ошибки доступа к ресурсу.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.FORBIDDEN, "Access denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Обрабатывает ошибки аутентификации и некорректные учетные данные.
     */
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class, JwtException.class})
    public ResponseEntity<Map<String, Object>> handleUnauthorized(Exception ex) {
        log.warn("Unauthorized access: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.UNAUTHORIZED,
                "Unauthorized: User does not exist or the entered data is incorrect");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Обрабатывает общие непредвиденные ошибки.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        Map<String, Object> response = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Обрабатывает ошибку, если пользователь не найден.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}