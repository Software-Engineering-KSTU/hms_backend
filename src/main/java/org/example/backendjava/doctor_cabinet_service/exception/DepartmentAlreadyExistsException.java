package org.example.backendjava.doctor_cabinet_service.exception;

/**
 * Исключение при попытке создать отделение с уже существующим названием.
 */
public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}