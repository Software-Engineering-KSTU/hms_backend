package org.example.backendjava.booking_to_doctore_service.exception;

public class DoctorAlreadyBookedException extends RuntimeException {
    public DoctorAlreadyBookedException(String message) {
        super(message);
    }
}