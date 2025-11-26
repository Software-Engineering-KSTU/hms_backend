package org.example.backendjava.booking_to_doctore_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.booking_to_doctore_service.model.dto.AppointmentRequestDto;
import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorAppiontmentResponseDto;
import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorResponseDto;
import org.example.backendjava.booking_to_doctore_service.model.dto.UpdateStatusRequestDto;
import org.example.backendjava.booking_to_doctore_service.model.entity.Appointment;
import org.example.backendjava.booking_to_doctore_service.model.entity.AppointmentStatus;
import org.example.backendjava.booking_to_doctore_service.service.AppointmentService;
import org.example.backendjava.booking_to_doctore_service.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAppointment(@RequestBody AppointmentRequestDto dto) {
        Appointment appointment = appointmentService.registerAppointment(dto);
        return ResponseEntity.ok("Appointment registered with ID: " + appointment.getId());
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getDoctorAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForDoctor(id));
    }

    /**
     * Новый эндпоинт для получения записей врача по статусу.
     * ID врача извлекается автоматически из токена аутентификации.
     *
     * @param status статус записи (SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW)
     * @return список записей с указанным статусом для текущего врача
     */
    @GetMapping("/doctor/by-status")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getDoctorAppointmentsByStatus(
            @RequestParam AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatusForCurrentDoctor(status));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForPatient(id));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    /**
     * Обновление статуса записи.
     * Доступно только врачам.
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<DoctorAppiontmentResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequestDto request) {
        DoctorAppiontmentResponseDto updated = appointmentService.updateAppointmentStatus(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }
}