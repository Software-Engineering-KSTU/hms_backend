package org.example.backendjava.auth_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backendjava.auth_service.model.dto.DoctorProfileResponseDto;
import org.example.backendjava.auth_service.model.dto.UpdateDoctorProfileDto;
import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.model.entity.User;
import org.example.backendjava.auth_service.repository.DoctorRepository;
import org.example.backendjava.auth_service.repository.UserRepository;
import org.example.backendjava.booking_to_doctore_service.exception.DoctorNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для управления профилем врача.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorProfileService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    /**
     * Получить профиль врача по userId.
     *
     * @param userId ID пользователя
     * @return DTO с данными профиля врача
     */
    @Transactional(readOnly = true)
    public DoctorProfileResponseDto getProfile(Long userId) {
        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor profile not found for user ID: " + userId));

        return mapToResponseDto(doctor);
    }

    /**
     * Обновить профиль врача.
     * Пользователь может обновлять только свой профиль.
     *
     * @param userId ID пользователя (из JWT)
     * @param targetUserId ID пользователя, профиль которого обновляется
     * @param dto DTO с новыми данными
     * @return обновлённый профиль
     */
    @Transactional
    public DoctorProfileResponseDto updateProfile(Long userId, Long targetUserId, UpdateDoctorProfileDto dto) {
        // Проверка прав: пользователь может редактировать только свой профиль
        if (!userId.equals(targetUserId)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor profile not found for user ID: " + userId));

        // Обновляем только те поля, которые переданы (не null)
        if (dto.getSpecialization() != null) {
            doctor.setSpecialization(dto.getSpecialization());
        }
        if (dto.getPhoneNumber() != null) {
            doctor.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getDateOfBirth() != null) {
            doctor.setBirthDate(dto.getDateOfBirth());
        }

        Doctor updated = doctorRepository.save(doctor);
        log.info("Doctor profile updated for user ID: {}", userId);

        return mapToResponseDto(updated);
    }

    /**
     * Маппинг сущности Doctor в DTO.
     */
    private DoctorProfileResponseDto mapToResponseDto(Doctor doctor) {
        User user = doctor.getUser();
        return new DoctorProfileResponseDto(
                doctor.getId(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                doctor.getSpecialization(),
                doctor.getPhoneNumber(),
                doctor.getBirthDate()
        );
    }
}