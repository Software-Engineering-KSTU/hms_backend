package org.example.backendjava.autth_service.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.autth_service.model.dto.*;
import org.example.backendjava.autth_service.model.entity.Role;
import org.example.backendjava.autth_service.model.entity.Token;
import org.example.backendjava.autth_service.model.entity.User;
import org.example.backendjava.autth_service.mapper.UserMapper;
import org.example.backendjava.autth_service.repository.DoctorRepository;
import org.example.backendjava.autth_service.repository.PatientRepository;
import org.example.backendjava.autth_service.repository.TokenRepository;
import org.example.backendjava.autth_service.repository.UserRepository;
import org.example.backendjava.autth_service.userexception.EmailAlreadyExistsException;
import org.example.backendjava.autth_service.userexception.UserNotFoundException;
import org.example.backendjava.autth_service.userexception.UsernameAlreadyExistsException;
import org.example.backendjava.autth_service.util.JwtUtil;
import org.example.backendjava.autth_service.model.entity.Doctor;
import org.example.backendjava.autth_service.model.entity.Patient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис аутентификации и регистрации пользователей.
 * Предоставляет методы для регистрации, аутентификации,
 * обновления токенов, сохранения и отзыва JWT токенов.
 */
@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    /**
     * Регистрирует нового пользователя.
     * Проверяет, что username и email уникальны.
     * Хэширует пароль, сохраняет пользователя и возвращает JWT токены.
     *
     * @param request DTO с данными пользователя (username, email, password, role)
     * @return AuthResponse с access и refresh токенами
     * @throws UsernameAlreadyExistsException если username уже существует
     * @throws EmailAlreadyExistsException    если email уже существует
     */
    public AuthResponse patientRegister(PatientRequestDto request) {
        checkExistingUser(request.getUsername(), request.getEmail());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user = repository.save(user);
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setAddress(request.getAddress());
        patient.setBirthDate(request.getDateOfBirth());
        patientRepository.save(patient);

        return saveTokenAndgetAuthResponse(user);
    }

    public AuthResponse doctorRegister(DoctorRequestDto request) {
        checkExistingUser(request.getUsername(), request.getEmail());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.DOCTOR);
        user = repository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialization(request.getSpecialization());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setBirthDate(request.getDateOfBirth());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctorRepository.save(doctor);
        return saveTokenAndgetAuthResponse(user);
    }

    /**
     * Аутентифицирует пользователя по username и паролю.
     * Генерирует новые JWT токены и отзывает старые.
     *
     * @param request DTO с username и password
     * @return AuthResponse с новыми access и refresh токенами
     * @throws UserNotFoundException если пользователь с указанным username не найден
     */
    public AuthResponse authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User with name: " + request.getUsername() + " does not exist"));

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, user);

        return new AuthResponse(accessToken, refreshToken);
    }

    /**
     * Обновляет access и refresh токены с использованием refresh токена.
     *
     * @param request HTTP запрос с заголовком Authorization: Bearer <refreshToken>
     * @return ResponseEntity с новым AuthResponse или UNAUTHORIZED, если токен некорректный
     */
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (jwtUtil.isValidRefreshToken(token, user)) {
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, user);

            AuthResponse response = new AuthResponse(accessToken, refreshToken);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Сохраняет JWT токен пользователя в базе данных.
     *
     * @param tokenStr JWT токен
     * @param user     пользователь, которому принадлежит токен
     */
    public void saveUserToken(String tokenStr, User user) {
        Token token = new Token();
        token.setAccessToken(tokenStr);
        token.setRevoked(false);
        token.setExpireDate(jwtUtil.extractExpiration(tokenStr));
        token.setUser(user);
        tokenRepository.save(token);
    }

    /**
     * Отзывает все активные токены пользователя.
     *
     * @param user пользователь, токены которого нужно отозвать
     */
    public void revokeAllTokenByUser(User user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokenByUser(user.getId());
        if (!validTokenListByUser.isEmpty()) {
            validTokenListByUser.forEach(t -> t.setRevoked(true));
        }
    }

    private AuthResponse saveTokenAndgetAuthResponse(User user) {

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        saveUserToken(accessToken, user);
        return new AuthResponse(accessToken, refreshToken);
    }

    public void checkExistingUser(String username, String email) {
        if (repository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("User with name: " + username + " already exists");
        }
        if (repository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("User with email: " + username + " already exists");
        }
    }
}
