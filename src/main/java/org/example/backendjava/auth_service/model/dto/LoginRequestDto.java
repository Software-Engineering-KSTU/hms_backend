package org.example.backendjava.auth_service.model.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto(String patient1, String number) {
    }
}
