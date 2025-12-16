package org.example.backendjava.auth_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для запроса на блокировку пользователя.
 */
@Data
public class BlockUserRequestDto {

    @NotBlank(message = "Block reason cannot be empty")
    @Size(max = 500, message = "Block reason must not exceed 500 characters")
    private String reason;
}