package org.example.backendjava.auth_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Сущность для хранения токенов сброса пароля.
 * Токен действителен 30 минут и может быть использован только один раз.
 */
@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальный токен для сброса пароля (UUID).
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * Пользователь, которому принадлежит токен.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Дата и время истечения токена.
     */
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    /**
     * Флаг использования токена (одноразовый).
     */
    @Column(nullable = false)
    private boolean used = false;

    /**
     * Дата создания токена.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;
}