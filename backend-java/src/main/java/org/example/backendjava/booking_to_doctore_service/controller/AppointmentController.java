package org.example.backendjava.booking_to_doctore_service.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/doctor/by-status/{status}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getDoctorAppointmentsByStatus(
            @PathVariable AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatusForCurrentDoctor(status));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getPatientAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForPatient(id));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<DoctorAppiontmentResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequestDto request) {
        DoctorAppiontmentResponseDto updated = appointmentService.updateAppointmentStatus(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/slots/{doctorId}")
    public ResponseEntity<List<SlotDto>> getAllSlots(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getCurrentStatusOfDates(doctorId));
    }

    @GetMapping("/doctor/current-date/{dateTime}")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getPatientAppointments(@PathVariable LocalDate dateTime) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDateForCurrentDoctor(dateTime));
    }

}