package org.example.backendjava.autth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backendjava.autth_service.entity.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "username can't be empty")
    private String username;

    @Email(message = "not correct format of email")
    @NotBlank(message = "email can't be empty")
    private String email;

    @NotBlank(message = "password can't be empty")
    private String password;

    @NotNull(message = "role can't be empty")
    private Role role;
}
