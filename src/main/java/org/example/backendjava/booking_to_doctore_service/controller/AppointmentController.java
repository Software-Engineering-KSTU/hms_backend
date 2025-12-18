package org.example.backendjava.booking_to_doctore_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backendjava.booking_to_doctore_service.model.dto.*;
import org.example.backendjava.booking_to_doctore_service.model.entity.Appointment;
import org.example.backendjava.booking_to_doctore_service.model.entity.AppointmentStatus;
import org.example.backendjava.booking_to_doctore_service.service.AppointmentService;
import org.example.backendjava.booking_to_doctore_service.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointments", description = "Управление записями к врачу")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @Operation(summary = "Записать пациента к врачу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerAppointment(@RequestBody AppointmentRequestDto dto) {
        Appointment appointment = appointmentService.registerAppointment(dto);
        String responseMessage = "Appointment registered with ID: " + appointment.getId();
        return ResponseEntity.ok(responseMessage);
    }

    @Operation(summary = "Получить все записи текущего врача")
    @GetMapping("/doctor")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getDoctorAppointments() {
        return ResponseEntity.ok(appointmentService.getAppointmentsForDoctor());
    }

    @Operation(summary = "Получить записи врача по статусу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список записей"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён")
    })
    @GetMapping("/doctor/by-status/{status}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getDoctorAppointmentsByStatus(
            @PathVariable AppointmentStatus status) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsByStatusForCurrentDoctor(status)
        );
    }

    @Operation(summary = "Получить записи пациента по ID")
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getPatientAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForPatient(id));
    }

    @Operation(summary = "Получить список всех врачей")
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    @Operation(summary = "Обновить статус записи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус обновлён"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<DoctorAppiontmentResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequestDto request) {
        DoctorAppiontmentResponseDto updated =
                appointmentService.updateAppointmentStatus(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Получить все слоты врача")
    @GetMapping("/slots/{doctorId}")
    public ResponseEntity<List<SlotDto>> getAllSlots(@PathVariable Long doctorId) {
        return ResponseEntity.ok(
                appointmentService.getCurrentStatusOfDates(doctorId)
        );
    }

    @Operation(summary = "Получить слоты врача на конкретный день")
    @PostMapping("/slots/current-day")
    public ResponseEntity<List<SlotDto>> getAllSlotsByDay(
            @RequestBody CurrentDoctorRequestDto dto) {
        return ResponseEntity.ok(
                appointmentService.getCurrentStatusOfDatesDay(dto)
        );
    }

    @Operation(summary = "Получить записи врача на указанную дату")
    @GetMapping("/doctor/current-date/{dateTime}")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getDoctorAppointmentsByDate(
            @PathVariable LocalDate dateTime) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsByDateForCurrentDoctor(dateTime)
        );
    }

    @Operation(summary = "Получить информацию о враче по ID")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.findDoctorById(doctorId));
    }
}
