package org.example.backendjava.autth_service.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorRequestDto {

    @NotBlank(message = "username can't be empty")
    private String username;
    @Email(message = "not correct format of email")
    @NotBlank(message = "email can't be empty")
    private String email;

    @NotBlank(message = "password can't be empty")
    private String password;
    private String specialization;
    private String phoneNumber;
    private LocalDate dateOfBirth;
}
