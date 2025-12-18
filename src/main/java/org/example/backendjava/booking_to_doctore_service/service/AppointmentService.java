package org.example.backendjava.booking_to_doctore_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // <--- 1. Добавил импорт для логов
import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.model.entity.Patient;
import org.example.backendjava.auth_service.model.entity.User;
import org.example.backendjava.auth_service.repository.DoctorRepository;
import org.example.backendjava.auth_service.repository.PatientRepository;
import org.example.backendjava.auth_service.repository.UserRepository;
import org.example.backendjava.auth_service.userexception.UserNotFoundException;
import org.example.backendjava.booking_to_doctore_service.exception.AppointmentNotFoundException;
import org.example.backendjava.booking_to_doctore_service.exception.DoctorAlreadyBookedException;
import org.example.backendjava.booking_to_doctore_service.exception.DoctorNotFoundException;
import org.example.backendjava.booking_to_doctore_service.exception.PatientNotFoundException;
import org.example.backendjava.booking_to_doctore_service.mapper.AppointmentMapper;
import org.example.backendjava.booking_to_doctore_service.model.dto.AppointmentRequestDto;
import org.example.backendjava.booking_to_doctore_service.model.dto.CurrentDoctorRequestDto;
import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorAppiontmentResponseDto;
import org.example.backendjava.booking_to_doctore_service.model.dto.SlotDto;
import org.example.backendjava.booking_to_doctore_service.model.entity.Appointment;
import org.example.backendjava.booking_to_doctore_service.model.entity.AppointmentStatus;
import org.example.backendjava.booking_to_doctore_service.model.entity.CurrentPatientStatus;
import org.example.backendjava.booking_to_doctore_service.repository.AppointmentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j // <--- 2. Включаем логирование Lombok
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public Appointment registerAppointment(AppointmentRequestDto dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + dto.getDoctorId() + " not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findIdByUsername(username);

        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with user id: " + userId + " not found"));


        LocalDateTime appointmentDateTime = LocalDateTime.of(dto.getDate(), dto.getTime());


        if (appointmentRepository.existsByDoctorIdAndDateTime(dto.getDoctorId(), appointmentDateTime)) {
            throw new DoctorAlreadyBookedException("Doctor is already booked at this time: " + appointmentDateTime);
        }

        CurrentPatientStatus status = new CurrentPatientStatus();
        status.setStatus(AppointmentStatus.SCHEDULED);
        status.setSymptomsDescribedByPatient(dto.getSymptomsDescribedByPatient());
        status.setSelfTreatmentMethodsTaken(dto.getSelfTreatmentMethodsTaken());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(appointmentDateTime); // Сохраняем склеенное время
        appointment.setCurrentPatientStatus(status);

        return appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public List<DoctorAppiontmentResponseDto> getAppointmentsForDoctor() {
        String username =  SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findIdByUsername(username);
        Doctor doctorId = doctorRepository.findByUserId(userId).orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + userId + " not found"));

        return appointmentRepository.findByDoctorId(doctorId.getId())
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DoctorAppiontmentResponseDto> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(appointmentMapper::toDoctorAppointmentResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DoctorAppiontmentResponseDto> getAppointmentsByStatusForCurrentDoctor(AppointmentStatus status) {
        Long doctorId = getCurrentDoctorId();

        return appointmentRepository.findByDoctorIdAndStatus(doctorId, status)
                .stream()
                .map(appointmentMapper::toDoctorAppointmentResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DoctorAppiontmentResponseDto> getAppointmentsByDateForCurrentDoctor(LocalDate date) {
        Long doctorId = getCurrentDoctorId();

        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(appointmentMapper::toDoctorAppointmentResponseDto)
                .filter(dto -> dto.getDateTime().toLocalDate().equals(date))
                .toList();
    }

    @Transactional
    public DoctorAppiontmentResponseDto updateAppointmentStatus(Long appointmentId, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id " + appointmentId + " not found"));

        CurrentPatientStatus currentStatus = appointment.getCurrentPatientStatus();
        currentStatus.setStatus(newStatus);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toDto(savedAppointment);
    }

    private Long getCurrentDoctorId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DoctorNotFoundException("User with username: " + username + " not found"));

        Doctor doctor = doctorRepository.findByUserId(user.getId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor profile not found for user: " + username));

        return doctor.getId();
    }

    public List<SlotDto> getCurrentStatusOfDates(Long doctorId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));

        Patient patient = patientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new PatientNotFoundException("Patient with id: " + user.getId() + " not found"));

        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        List<SlotDto> dates = new ArrayList<>();

        for (Appointment appointment : appointments) {
            // Добавил проверку на битую дату и здесь тоже
            if (appointment.getDateTime() == null) {
                log.warn("getCurrentStatusOfDates: Skipping corrupted appointment (Date is missing). ID: {}", appointment.getId());
                continue;
            }

            if (appointment.getCurrentPatientStatus() == null || appointment.getCurrentPatientStatus().getStatus() == null) {
                log.warn("getCurrentStatusOfDates: Skipping corrupted appointment (Status is missing). ID: {}", appointment.getId());
                continue;
            }

            AppointmentStatus status = appointment.getCurrentPatientStatus().getStatus();

            if (status == AppointmentStatus.SCHEDULED || status == AppointmentStatus.IN_PROGRESS) {
                String slotStatus = appointment.getPatient().getId().equals(patient.getId()) ? "mine" : "other";
                dates.add(new SlotDto(appointment.getDateTime(), slotStatus));
            }
        }

        return dates;
    }

    public List<SlotDto> getCurrentStatusOfDatesDay(CurrentDoctorRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));

        Patient patient = patientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new PatientNotFoundException("Patient with id: " + user.getId() + " not found"));

        List<Appointment> appointments = appointmentRepository.findByDoctorId(dto.getDoctorId());

        List<SlotDto> dates = new ArrayList<>();

        for (Appointment appointment : appointments) {
            // --- ИСПРАВЛЕНИЕ: Логируем и пропускаем битую запись ---
            if (appointment.getDateTime() == null) {
                log.error("CRITICAL DATA ERROR: Appointment ID {} has NULL DateTime. Skipping.", appointment.getId());
                continue; // Просто идем дальше, не ломая ответ для фронта
            }

            // Фильтр по дате
            if (!appointment.getDateTime().toLocalDate().equals(dto.getDate())) {
                continue;
            }

            // --- ИСПРАВЛЕНИЕ: Логируем и пропускаем запись без статуса ---
            if (appointment.getCurrentPatientStatus() == null || appointment.getCurrentPatientStatus().getStatus() == null) {
                log.error("CRITICAL DATA ERROR: Appointment ID {} has NULL Status. Skipping.", appointment.getId());
                continue;
            }

            // --- Нормальная обработка ---
            AppointmentStatus status = appointment.getCurrentPatientStatus().getStatus();

            if (status == AppointmentStatus.SCHEDULED || status == AppointmentStatus.IN_PROGRESS) {
                String slotStatus = appointment.getPatient().getId().equals(patient.getId()) ? "mine" : "other";
                dates.add(new SlotDto(appointment.getDateTime(), slotStatus));
            }
        }

        return dates;
    }
}