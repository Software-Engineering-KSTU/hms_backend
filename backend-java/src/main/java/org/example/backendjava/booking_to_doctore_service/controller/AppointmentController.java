package org.example.backendjava.booking_to_doctore_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.booking_to_doctore_service.model.dto.AppointmentRequestDto;
import org.example.backendjava.booking_to_doctore_service.model.entity.Appointment;
import org.example.backendjava.booking_to_doctore_service.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAppointment(@RequestBody AppointmentRequestDto dto) {
        Appointment appointment = appointmentService.registerAppointment(dto);
        return ResponseEntity.ok("Appointment registered with ID: " + appointment.getId());
    }

    @GetMapping("/doctor/{id}")
    public List<Appointment> getDoctorAppointments(@PathVariable Long id) {
        return appointmentService.getAppointmentsForDoctor(id);
    }

    @GetMapping("/patient/{id}")
    public List<Appointment> getPatientAppointments(@PathVariable Long id) {
        return appointmentService.getAppointmentsForPatient(id);
    }

}
