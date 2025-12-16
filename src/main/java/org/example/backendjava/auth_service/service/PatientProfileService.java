package org.example.backendjava.auth_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backendjava.auth_service.model.dto.PatientProfileResponseDto;
import org.example.backendjava.auth_service.model.dto.UpdatePatientProfileDto;
import org.example.backendjava.auth_service.model.entity.Patient;
import org.example.backendjava.auth_service.model.entity.User;
import org.example.backendjava.auth_service.repository.PatientRepository;
import org.example.backendjava.auth_service.repository.UserRepository;
import org.example.backendjava.booking_to_doctore_service.exception.PatientNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для управления профилем пациента.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PatientProfileService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    /**
     * Получить профиль пациента по userId.
     *
     * @param userId ID пользователя
     * @return DTO с данными профиля пациента
     */
    @Transactional(readOnly = true)
    public PatientProfileResponseDto getProfile(Long userId) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient profile not found for user ID: " + userId));

        return mapToResponseDto(patient);
    }

    /**
     * Обновить профиль пациента.
     * Пользователь может обновлять только свой профиль.
     *
     * @param userId ID пользователя (из JWT)
     * @param targetUserId ID пользователя, профиль которого обновляется
     * @param dto DTO с новыми данными
     * @return обновлённый профиль
     */
    @Transactional
    public PatientProfileResponseDto updateProfile(Long userId, Long targetUserId, UpdatePatientProfileDto dto) {
        // Проверка прав: пользователь может редактировать только свой профиль
        if (!userId.equals(targetUserId)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient profile not found for user ID: " + userId));

        // Обновляем только те поля, которые переданы (не null)
        if (dto.getPhoneNumber() != null) {
            patient.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getAddress() != null) {
            patient.setAddress(dto.getAddress());
        }
        if (dto.getDateOfBirth() != null) {
            patient.setBirthDate(dto.getDateOfBirth());
        }

        Patient updated = patientRepository.save(patient);
        log.info("Patient profile updated for user ID: {}", userId);

        return mapToResponseDto(updated);
    }

    /**
     * Маппинг сущности Patient в DTO.
     */
    private PatientProfileResponseDto mapToResponseDto(Patient patient) {
        User user = patient.getUser();
        return new PatientProfileResponseDto(
                patient.getId(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                patient.getPhoneNumber(),
                patient.getAddress(),
                patient.getBirthDate()
        );
    }
}