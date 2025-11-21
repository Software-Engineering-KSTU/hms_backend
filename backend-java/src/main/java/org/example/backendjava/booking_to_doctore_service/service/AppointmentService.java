package org.example.backendjava.booking_to_doctore_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.autth_service.repository.UserRepository;
import org.example.backendjava.booking_to_doctore_service.exception.DoctorAlreadyBookedException;
import org.example.backendjava.booking_to_doctore_service.exception.DoctorNotFoundException;
import org.example.backendjava.booking_to_doctore_service.exception.PatientNotFoundException;
import org.example.backendjava.booking_to_doctore_service.mapper.AppointmentMapper;
import org.example.backendjava.booking_to_doctore_service.model.dto.AppointmentRequestDto;
import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorAppiontmentResponseDto;
import org.example.backendjava.booking_to_doctore_service.model.entity.Appointment;
import org.example.backendjava.autth_service.model.entity.Doctor;
import org.example.backendjava.autth_service.model.entity.Patient;
import org.example.backendjava.booking_to_doctore_service.repository.AppointmentRepository;
import org.example.backendjava.autth_service.repository.DoctorRepository;
import org.example.backendjava.autth_service.repository.PatientRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    public Appointment registerAppointment(AppointmentRequestDto dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + dto.getDoctorId() + " not found"));

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id = userRepository.findIdByUsername(name);

        Patient patient = patientRepository.findByUserId(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with user id: " + id + " not found"));

        if (appointmentRepository.existsByDoctorIdAndDateTime(doctor.getId(), dto.getDateTime())) {
            throw new DoctorAlreadyBookedException("Doctor is already booked at this time: " + dto.getDateTime());
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(dto.getDateTime());
        appointment.setStatus("");

        return appointmentRepository.save(appointment);
    }

    public List<DoctorAppiontmentResponseDto> getAppointmentsForDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream().map(appointmentMapper::toDto)
                .toList();
    }

    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

}