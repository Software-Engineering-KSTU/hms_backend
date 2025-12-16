package org.example.backendjava.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.service.UserManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST-контроллер для управления пользователями.
 * Доступен только для администраторов.
 */
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Управление пользователями (только для администраторов)")
public class UserManagementController {

    private final UserManagementService userManagementService;

    /**
     * Удалить пациента из системы.
     * Каскадно удаляются: appointments, visits.
     *
     * @param userId ID пользователя-пациента
     * @return сообщение об успешном удалении
     */
    @DeleteMapping("/patient/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Удалить пациента",
            description = "Полностью удаляет пациента из системы. " +
                    "Каскадно удаляются все записи к врачам (appointments) и визиты (visits)."
    )
    public ResponseEntity<Map<String, String>> deletePatient(@PathVariable Long userId) {
        userManagementService.deletePatient(userId);
        return ResponseEntity.ok(Map.of(
                "message", "Patient with ID " + userId + " has been successfully deleted"
        ));
    }

    /**
     * Удалить врача из системы.
     * Каскадно удаляются: DoctorResume, appointments, visits.
     *
     * @param userId ID пользователя-врача
     * @return сообщение об успешном удалении
     */
    @DeleteMapping("/doctor/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Удалить врача",
            description = "Полностью удаляет врача из системы. " +
                    "Каскадно удаляются резюме (DoctorResume), записи с пациентами (appointments) и визиты (visits)."
    )
    public ResponseEntity<Map<String, String>> deleteDoctor(@PathVariable Long userId) {
        userManagementService.deleteDoctor(userId);
        return ResponseEntity.ok(Map.of(
                "message", "Doctor with ID " + userId + " has been successfully deleted"
        ));
    }

    /**
     * Удалить любого пользователя (автоматическое определение типа).
     *
     * @param userId ID пользователя
     * @return сообщение об успешном удалении
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя любого типа (пациент/врач). " +
                    "Автоматически определяет тип пользователя и выполняет каскадное удаление. " +
                    "Администраторов удалить нельзя."
    )
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId) {
        userManagementService.deleteUser(userId);
        return ResponseEntity.ok(Map.of(
                "message", "User with ID " + userId + " has been successfully deleted"
        ));
    }
}