package org.example.backendjava.auth_service.repository;

import org.example.backendjava.auth_service.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Репозиторий для работы с токенами сброса пароля.
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Находит токен по его значению.
     *
     * @param token значение токена
     * @return Optional с токеном, если найден
     */
    Optional<PasswordResetToken> findByToken(String token);

    /**
     * Находит все неиспользованные токены для пользователя.
     *
     * @param userId ID пользователя
     * @return список токенов
     */
    @Query("SELECT t FROM PasswordResetToken t WHERE t.user.id = :userId AND t.used = false")
    Optional<PasswordResetToken> findActiveTokenByUserId(Long userId);

    /**
     * Удаляет все истёкшие токены.
     *
     * @param now текущая дата и время
     */
    @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.expiryDate < :now")
    void deleteExpiredTokens(LocalDateTime now);

    /**
     * Удаляет все токены пользователя.
     *
     * @param userId ID пользователя
     */
    @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.user.id = :userId")
    void deleteAllByUserId(Long userId);
}