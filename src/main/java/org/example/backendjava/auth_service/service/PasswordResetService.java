package org.example.backendjava.auth_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backendjava.auth_service.model.dto.ForgotPasswordRequestDto;
import org.example.backendjava.auth_service.model.dto.PasswordResetResponseDto;
import org.example.backendjava.auth_service.model.dto.ResetPasswordRequestDto;
import org.example.backendjava.auth_service.model.entity.PasswordResetToken;
import org.example.backendjava.auth_service.model.entity.User;
import org.example.backendjava.auth_service.repository.PasswordResetTokenRepository;
import org.example.backendjava.auth_service.repository.TokenRepository;
import org.example.backendjava.auth_service.repository.UserRepository;
import org.example.backendjava.auth_service.userexception.InvalidTokenException;
import org.example.backendjava.auth_service.userexception.TokenAlreadyUsedException;
import org.example.backendjava.auth_service.userexception.TokenExpiredException;
import org.example.backendjava.auth_service.userexception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сервис для управления сбросом пароля.
 * Генерирует токены, проверяет их валидность и выполняет сброс пароля.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Время действия токена в минутах.
     */
    private static final int TOKEN_EXPIRY_MINUTES = 30;

    /**
     * Создаёт токен для сброса пароля.
     * Если у пользователя уже есть активный токен, он будет удалён.
     *
     * @param request DTO с email пользователя
     * @return DTO с токеном и информацией о сроке действия
     */
    @Transactional
    public PasswordResetResponseDto createPasswordResetToken(ForgotPasswordRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(
                        "User with email: " + request.getEmail() + " not found"));

        // Удаляем старые неиспользованные токены пользователя
        passwordResetTokenRepository.deleteAllByUserId(user.getId());

        // Генерируем новый токен
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(expiryDate);
        resetToken.setUsed(false);
        resetToken.setCreatedAt(LocalDateTime.now());

        passwordResetTokenRepository.save(resetToken);

        log.info("Password reset token created for user: {}", user.getEmail());

        return new PasswordResetResponseDto(
                token,
                "Password reset token created successfully. Token expires in " + TOKEN_EXPIRY_MINUTES + " minutes.",
                expiryDate
        );
    }

    /**
     * Сбрасывает пароль пользователя по токену.
     * Аннулирует все активные JWT токены пользователя.
     *
     * @param request DTO с токеном и новым паролем
     */
    @Transactional
    public void resetPassword(ResetPasswordRequestDto request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new InvalidTokenException("Invalid password reset token"));

        // Проверка: токен уже использован
        if (resetToken.isUsed()) {
            throw new TokenAlreadyUsedException("This token has already been used");
        }

        // Проверка: токен истёк
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Password reset token has expired");
        }

        User user = resetToken.getUser();

        // Обновляем пароль
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Помечаем токен как использованный
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        // Аннулируем все активные JWT токены пользователя
        revokeAllUserTokens(user);

        log.info("Password successfully reset for user: {}", user.getEmail());
    }

    /**
     * Аннулирует все активные JWT токены пользователя.
     *
     * @param user пользователь
     */
    private void revokeAllUserTokens(User user) {
        tokenRepository.findAllTokenByUser(user.getId())
                .forEach(token -> {
                    token.setRevoked(true);
                    tokenRepository.save(token);
                });
    }

    /**
     * Удаляет все истёкшие токены (вызывается по расписанию).
     */
    @Transactional
    public void cleanupExpiredTokens() {
        passwordResetTokenRepository.deleteExpiredTokens(LocalDateTime.now());
        log.info("Expired password reset tokens cleaned up");
    }
}