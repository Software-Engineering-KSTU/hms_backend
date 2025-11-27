package org.example.backendjava.auth_service.model.dto;

import lombok.*;
import org.example.backendjava.auth_service.model.entity.Role;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}

