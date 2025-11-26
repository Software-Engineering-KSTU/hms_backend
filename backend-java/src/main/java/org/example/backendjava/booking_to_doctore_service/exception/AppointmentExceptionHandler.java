package org.example.backendjava.booking_to_doctore_service.exception;

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
 * Глобальный обработчик исключений для операций с записями к врачам.
 * <p>
 * Перехватывает ошибки поиска врачей, пациентов, записей и конфликты при записи,
 * формируя стандартизированный JSON-ответ с информацией об ошибке.
 */
@Order(1)
@ControllerAdvice
public class AppointmentExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AppointmentExceptionHandler.class);

    /**
     * Обрабатывает ошибку, когда врач не найден.
     */
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDoctorNotFoundException(DoctorNotFoundException ex) {
        log.warn("Doctor not found: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Обрабатывает ошибку, когда пациент не найден.
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePatientNotFoundException(PatientNotFoundException ex) {
        log.warn("Patient not found: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Обрабатывает ошибку, когда запись к врачу не найдена.
     */
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAppointmentNotFoundException(AppointmentNotFoundException ex) {
        log.warn("Appointment not found: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Обрабатывает ошибку, когда врач уже занят в указанное время.
     */
    @ExceptionHandler(DoctorAlreadyBookedException.class)
    public ResponseEntity<Map<String, Object>> handleDoctorAlreadyBookedException(DoctorAlreadyBookedException ex) {
        log.warn("Booking conflict: {}", ex.getMessage());
        Map<String, Object> response = createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}