package org.example.backendjava.auth_service.userexception;

/**
 * Исключение выбрасывается когда токен сброса пароля недействителен.
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}