package org.example.backendjava.autth_service.model.dto;

import lombok.*;
import org.example.backendjava.autth_service.model.entity.Role;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}

