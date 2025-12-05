package org.example.backendjava.booking_to_doctore_service.service;

import lombok.RequiredArgsConstructor;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalTime;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {
//
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public Appointment registerAppointment(AppointmentRequestDto dto) {
        // 1. Поиск доктора
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + dto.getDoctorId() + " not found"));

        // 2. Поиск пациента
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findIdByUsername(username);

        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with user id: " + userId + " not found"));

        // 3. Сборка и Валидация Времени

        // Берем дату из DTO
        LocalDate date = dto.getDate();
        // Берем время из DTO и принудительно обнуляем секунды и наносекунды
        LocalTime time = dto.getTime().withSecond(0).withNano(0);

        // Собираем полную дату-время для проверки и сохранения
        LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);

        // Получаем текущее время сервера (без учета часовых поясов, как "настенные часы")
        LocalDateTime now = LocalDateTime.now();

        // ПРОВЕРКА: Нельзя записаться на время, которое уже прошло
        // Это покрывает два случая:
        // а) Дата в прошлом (вчера и ранее)
        // б) Дата сегодня, но время меньше текущего (сейчас 12:00, пытаемся на 11:00)
        if (appointmentDateTime.isBefore(now)) {
            throw new IllegalArgumentException("Нельзя записаться на прошедшее время. Текущее время: " + now);
        }

        // 4. Проверка занятости слота в БД
        if (appointmentRepository.existsByDoctorIdAndDateTime(dto.getDoctorId(), appointmentDateTime)) {
            throw new DoctorAlreadyBookedException("Доктор уже занят на это время: " + appointmentDateTime);
        }

        // 5. Создание статуса
        CurrentPatientStatus status = new CurrentPatientStatus();
        status.setStatus(AppointmentStatus.SCHEDULED);
        status.setSymptomsDescribedByPatient(dto.getSymptomsDescribedByPatient());
        status.setSelfTreatmentMethodsTaken(dto.getSelfTreatmentMethodsTaken());

        // 6. Сохранение записи
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        // Сохраняем собранный LocalDateTime, так как сущность Appointment в БД
        // скорее всего ожидает именно этот формат.
        appointment.setDateTime(appointmentDateTime);

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
            if (appointment.getCurrentPatientStatus() == null || appointment.getCurrentPatientStatus().getStatus() == null) {
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
            if (!appointment.getDateTime().toLocalDate().equals(dto.getDate())) {
                continue;
            }

            if (appointment.getCurrentPatientStatus() == null || appointment.getCurrentPatientStatus().getStatus() == null) {
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


}