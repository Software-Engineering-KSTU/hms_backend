package org.example.backendjava.auth_service.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backendjava.auth_service.model.dto.*;
import org.example.backendjava.auth_service.model.entity.Role;
import org.example.backendjava.auth_service.model.entity.User;
import org.example.backendjava.auth_service.repository.*;
import org.example.backendjava.auth_service.userexception.UsernameAlreadyExistsException;
import org.example.backendjava.auth_service.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest httpRequest;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void patientRegister_success() {
        PatientRequestDto req = new PatientRequestDto("pat1", "p@mail.ru", "pass", "79001234567", "SPb", "1995-03-03");

        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(jwtUtil.generateAccessToken(any())).thenReturn("access");
        when(jwtUtil.generateRefreshToken(any())).thenReturn("refresh");

        AuthResponse resp = authenticationService.patientRegister(req);

        assertNotNull(resp.getAccessToken());
        assertNotNull(resp.getRefreshToken());
        verify(patientRepository).save(any());
        verify(tokenRepository).save(any());
    }

    @Test
    void patientRegister_usernameExists_throws() {
        PatientRequestDto req = new PatientRequestDto("exists", "new@mail.ru", "pass", "7900", "City", "2000-01-01");

        when(userRepository.findByUsername("exists")).thenReturn(Optional.of(new User()));

        assertThrows(UsernameAlreadyExistsException.class,
                () -> authenticationService.patientRegister(req));
    }

    @Test
    void doctorRegister_success() {
        DoctorRequestDto req = new DoctorRequestDto("doc", "doc@mail.ru", "pass", "Neurology", "7900", "1975-10-10");

        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(jwtUtil.generateAccessToken(any())).thenReturn("access");
        when(jwtUtil.generateRefreshToken(any())).thenReturn("refresh");

        AuthResponse resp = authenticationService.doctorRegister(req);

        assertNotNull(resp.getAccessToken());
        verify(doctorRepository).save(any());
    }

    @Test
    void authenticate_success() {
        LoginRequestDto req = new LoginRequestDto("user", "pass");
        User user = new User();
        user.setUsername("user");
        user.setRole(Role.USER);

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(jwtUtil.generateAccessToken(any())).thenReturn("access");
        when(jwtUtil.generateRefreshToken(any())).thenReturn("refresh");

        AuthResponse resp = authenticationService.authenticate(req);

        assertEquals("access", resp.getAccessToken());
        verify(tokenRepository).save(any());
        verify(tokenRepository).findAllTokenByUser(anyLong());
    }

    @Test
    void refresh_validToken_returnsNewTokens() {
        when(httpRequest.getHeader("Authorization")).thenReturn("Bearer valid-refresh");
        when(jwtUtil.extractUsername("valid-refresh")).thenReturn("user");
        User user = new User();
        user.setUsername("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(jwtUtil.isValidRefreshToken("valid-refresh", user)).thenReturn(true);
        when(jwtUtil.generateAccessToken(any())).thenReturn("new-access");
        when(jwtUtil.generateRefreshToken(any())).thenReturn("new-refresh");

        ResponseEntity<AuthResponse> resp = authenticationService.refresh(httpRequest);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("new-access", resp.getBody().getAccessToken());
    }

    @Test
    void refresh_invalidHeader_returns401() {
        when(httpRequest.getHeader("Authorization")).thenReturn(null);

        ResponseEntity<AuthResponse> resp = authenticationService.refresh(httpRequest);

        assertEquals(401, resp.getStatusCodeValue());
    }
}