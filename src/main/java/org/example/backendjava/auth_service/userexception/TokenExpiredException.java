package org.example.backendjava.auth_service.userexception;

/**
 * Исключение выбрасывается когда токен сброса пароля истёк.
 */
public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}