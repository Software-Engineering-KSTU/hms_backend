package org.example.backendjava.doctor_cabinet_service.exception;

/**
 * Исключение когда отделение не найдено.
 */
public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}