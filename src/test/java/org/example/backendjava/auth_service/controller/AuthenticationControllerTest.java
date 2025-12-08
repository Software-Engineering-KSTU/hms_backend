package org.example.backendjava.auth_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backendjava.auth_service.model.dto.*;
import org.example.backendjava.auth_service.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void patientRegistration_success() throws Exception {
        PatientRequestDto req = new PatientRequestDto("patient1", "p@mail.ru", "123456", "79123456789", "Moscow", "1990-01-01");
        AuthResponse resp = new AuthResponse("access-jwt", "refresh-jwt");

        when(authenticationService.patientRegister(any())).thenReturn(resp);

        mockMvc.perform(post("/api/auth/patient-register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-jwt"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-jwt"));
    }

    @Test
    void doctorRegistration_success() throws Exception {
        DoctorRequestDto req = new DoctorRequestDto("doc1", "d@mail.ru", "123456", "Cardiology", "79991234567", "1985-05-05");
        AuthResponse resp = new AuthResponse("access-jwt", "refresh-jwt");

        when(authenticationService.doctorRegister(any())).thenReturn(resp);

        mockMvc.perform(post("/api/auth/doctor-register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-jwt"));
    }

    @Test
    void login_success() throws Exception {
        LoginRequestDto req = new LoginRequestDto("patient1", "123456");
        AuthResponse resp = new AuthResponse("access-jwt", "refresh-jwt");

        when(authenticationService.authenticate(any())).thenReturn(resp);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    void refreshToken_success() throws Exception {
        AuthResponse resp = new AuthResponse("new-access-jwt", "new-refresh-jwt");

        when(authenticationService.refresh(any())).thenReturn(ResponseEntity.ok(resp));

        mockMvc.perform(post("/api/auth/refresh-token")
                        .header("Authorization", "Bearer old-refresh-jwt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-access-jwt"));
    }
}