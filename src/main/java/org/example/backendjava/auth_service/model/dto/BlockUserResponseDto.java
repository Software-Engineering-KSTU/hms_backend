package org.example.backendjava.auth_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO для ответа при блокировке/разблокировке пользователя.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockUserResponseDto {

    private Long userId;
    private String username;
    private boolean isBlocked;
    private LocalDateTime blockedAt;
    private String blockReason;
    private String message;
}
