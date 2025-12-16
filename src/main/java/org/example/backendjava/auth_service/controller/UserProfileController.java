package org.example.backendjava.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.model.dto.*;
import org.example.backendjava.auth_service.model.entity.User;
import org.example.backendjava.auth_service.repository.UserRepository;
import org.example.backendjava.auth_service.service.DoctorProfileService;
import org.example.backendjava.auth_service.service.PatientProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления профилями пользователей.
 * Позволяет пациентам и врачам просматривать и обновлять свои профили.
 */
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Управление профилями пациентов и врачей")
public class UserProfileController {

    private final PatientProfileService patientProfileService;
    private final DoctorProfileService doctorProfileService;
    private final UserRepository userRepository;

    /**
     * Получить профиль текущего пациента.
     *
     * @param userDetails данные текущего пользователя из JWT
     * @return профиль пациента
     */
    @GetMapping("/patient")
    @Operation(
            summary = "Получить профиль пациента",
            description = "Возвращает профиль текущего авторизованного пациента"
    )
    public ResponseEntity<PatientProfileResponseDto> getPatientProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = getUserIdFromUserDetails(userDetails);
        PatientProfileResponseDto profile = patientProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    /**
     * Обновить профиль текущего пациента.
     *
     * @param userDetails данные текущего пользователя из JWT
     * @param dto DTO с новыми данными профиля
     * @return обновлённый профиль пациента
     */
    @PutMapping("/patient")
    @Operation(
            summary = "Обновить профиль пациента",
            description = "Обновляет профиль текущего авторизованного пациента"
    )
    public ResponseEntity<PatientProfileResponseDto> updatePatientProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdatePatientProfileDto dto) {

        Long userId = getUserIdFromUserDetails(userDetails);
        PatientProfileResponseDto updated = patientProfileService.updateProfile(userId, userId, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Получить профиль текущего врача.
     *
     * @param userDetails данные текущего пользователя из JWT
     * @return профиль врача
     */
    @GetMapping("/doctor")
    @Operation(
            summary = "Получить профиль врача",
            description = "Возвращает профиль текущего авторизованного врача"
    )
    public ResponseEntity<DoctorProfileResponseDto> getDoctorProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = getUserIdFromUserDetails(userDetails);
        DoctorProfileResponseDto profile = doctorProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    /**
     * Обновить профиль текущего врача.
     *
     * @param userDetails данные текущего пользователя из JWT
     * @param dto DTO с новыми данными профиля
     * @return обновлённый профиль врача
     */
    @PutMapping("/doctor")
    @Operation(
            summary = "Обновить профиль врача",
            description = "Обновляет профиль текущего авторизованного врача"
    )
    public ResponseEntity<DoctorProfileResponseDto> updateDoctorProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateDoctorProfileDto dto) {

        Long userId = getUserIdFromUserDetails(userDetails);
        DoctorProfileResponseDto updated = doctorProfileService.updateProfile(userId, userId, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Извлекает ID пользователя из UserDetails.
     */
    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return user.getId();
    }
}