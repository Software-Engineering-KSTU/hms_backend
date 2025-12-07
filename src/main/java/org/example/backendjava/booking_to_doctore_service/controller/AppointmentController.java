package org.example.backendjava.booking_to_doctore_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // <--- Добавьте этот импорт
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

@Slf4j // анотация для логирования
@RequiredArgsConstructor
@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    //
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAppointment(@RequestBody AppointmentRequestDto dto) {
        // 2. Логируем то, что пришло от клиента (Request)
        log.info("\n========== [NEW APPOINTMENT REQUEST] ==========\n" +
                        "Doctor ID: {}\n" +
                        "Date:      {}\n" +
                        "Time:      {}\n" +
                        "Symptoms:  {}\n" +
                        "Self Treat: {}",
                dto.getDoctorId(),
                dto.getDate(),
                dto.getTime(),
                dto.getSymptomsDescribedByPatient(),
                dto.getSelfTreatmentMethodsTaken()
        );

        Appointment appointment = appointmentService.registerAppointment(dto);

        String responseMessage = "Appointment registered with ID: " + appointment.getId();

        // 3. Логируем то, что отдаем клиенту (Response)
        log.info("\n========== [RESPONSE TO CLIENT] ==========\n" +
                        "Status:     200 OK\n" +
                        "Created ID: {}\n" +
                        "Message:    {}\n" +
                        "==========================================",
                appointment.getId(),
                responseMessage
        );

        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/doctor")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getDoctorAppointments() {
        return ResponseEntity.ok(appointmentService.getAppointmentsForDoctor());
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


    @PostMapping("/slots/current-day")
    public ResponseEntity<List<SlotDto>> getAllSlotsByDay(@RequestBody CurrentDoctorRequestDto dto) {
        return ResponseEntity.ok(appointmentService.getCurrentStatusOfDatesDay(dto));
    }

    @GetMapping("/doctor/current-date/{dateTime}")
    public ResponseEntity<List<DoctorAppiontmentResponseDto>> getPatientAppointments(@PathVariable LocalDate dateTime) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDateForCurrentDoctor(dateTime));
    }

}