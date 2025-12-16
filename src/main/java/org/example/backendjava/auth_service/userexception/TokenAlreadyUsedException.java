package org.example.backendjava.auth_service.userexception;

/**
 * Исключение выбрасывается когда токен уже был использован.
 */
public class TokenAlreadyUsedException extends RuntimeException {
    public TokenAlreadyUsedException(String message) {
        super(message);
    }
}
