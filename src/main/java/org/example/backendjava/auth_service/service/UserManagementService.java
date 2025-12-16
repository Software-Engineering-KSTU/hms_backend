package org.example.backendjava.auth_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.model.entity.Patient;
import org.example.backendjava.auth_service.model.entity.Role;
import org.example.backendjava.auth_service.model.entity.User;
import org.example.backendjava.auth_service.repository.DoctorRepository;
import org.example.backendjava.auth_service.repository.PatientRepository;
import org.example.backendjava.auth_service.repository.TokenRepository;
import org.example.backendjava.auth_service.repository.UserRepository;
import org.example.backendjava.auth_service.userexception.UserNotFoundException;
import org.example.backendjava.booking_to_doctore_service.exception.DoctorNotFoundException;
import org.example.backendjava.booking_to_doctore_service.exception.PatientNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для управления пользователями (удаление, блокировка).
 * Доступен только для администраторов.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final TokenRepository tokenRepository;

    /**
     * Удаляет пациента из системы.
     * Каскадно удаляются: appointments, visits.
     *
     * @param userId ID пользователя-пациента
     */
    @Transactional
    public void deletePatient(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // Проверяем, что это пациент
        if (user.getRole() != Role.USER) {
            throw new IllegalArgumentException("User with ID " + userId + " is not a patient");
        }

        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException("Patient profile not found for user ID: " + userId));

        // Удаляем все JWT токены пользователя
        tokenRepository.deleteAllByUserId(userId);

        // Удаляем пациента (каскадно удалятся appointments и visits)
        patientRepository.delete(patient);

        // Удаляем пользователя
        userRepository.delete(user);

        log.info("Patient with user ID {} has been deleted (cascaded: appointments, visits)", userId);
    }

    /**
     * Удаляет врача из системы.
     * Каскадно удаляются: DoctorResume, appointments, visits.
     *
     * @param userId ID пользователя-врача
     */
    @Transactional
    public void deleteDoctor(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // Проверяем, что это врач
        if (user.getRole() != Role.DOCTOR) {
            throw new IllegalArgumentException("User with ID " + userId + " is not a doctor");
        }

        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor profile not found for user ID: " + userId));

        // Удаляем все JWT токены пользователя
        tokenRepository.deleteAllByUserId(userId);

        // Удаляем врача (каскадно удалятся resume, appointments, visits)
        doctorRepository.delete(doctor);

        // Удаляем пользователя
        userRepository.delete(user);

        log.info("Doctor with user ID {} has been deleted (cascaded: resume, appointments, visits)", userId);
    }

    /**
     * Универсальное удаление пользователя (определяет тип автоматически).
     *
     * @param userId ID пользователя
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        switch (user.getRole()) {
            case USER -> deletePatient(userId);
            case DOCTOR -> deleteDoctor(userId);
            case ADMIN -> throw new IllegalArgumentException("Cannot delete admin users");
            default -> throw new IllegalArgumentException("Unknown user role");
        }
    }
}