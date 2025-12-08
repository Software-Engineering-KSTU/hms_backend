package org.example.backendjava.auth_service.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientRequestDto {

    @NotBlank(message = "username can't be empty")
    private String username;
    @Email(message = "not correct format of email")
    @NotBlank(message = "email can't be empty")
    private String email;

    @NotBlank(message = "password can't be empty")
    private String password;

    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;

    public PatientRequestDto(String patient1, String mail, String number, String number1, String moscow, String date) {

    }
}
