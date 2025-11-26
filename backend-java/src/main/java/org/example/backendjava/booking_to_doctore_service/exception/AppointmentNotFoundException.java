package org.example.backendjava.booking_to_doctore_service.exception;

/**
 * Исключение выбрасывается когда запись к врачу не найдена.
 */
public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}