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
import org.example.backendjava.booking_to_doctore_service.model.entity.AppointmentStatus;
import org.example.backendjava.booking_to_doctore_service.model.entity.CurrentPatientStatus;
import org.example.backendjava.booking_to_doctore_service.repository.AppointmentRepository;
import org.example.backendjava.autth_service.repository.DoctorRepository;
import org.example.backendjava.autth_service.repository.PatientRepository;
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

        // Проверяем, существует ли врач
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + dto.getDoctorId() + " not found"));

        // Получаем текущего пользователя (пациента)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findIdByUsername(username);

        // Проверяем, существует ли пациент
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with user id: " + userId + " not found"));

        // Проверяем, не занят ли врач в это время
        if (appointmentRepository.existsByDoctorIdAndDateTime(dto.getDoctorId(), dto.getDateTime())) {
            throw new DoctorAlreadyBookedException("Doctor is already booked at this time: " + dto.getDateTime());
        }

        // Создаем новый статус пациента
        CurrentPatientStatus status = new CurrentPatientStatus();
        status.setStatus(AppointmentStatus.SCHEDULED);
        status.setSymptomsDescribedByPatient(dto.getSymptomsDescribedByPatient());
        status.setSelfTreatmentMethodsTaken(dto.getSelfTreatmentMethodsTaken());

        // Создаем запись к врачу
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(dto.getDateTime());
        appointment.setCurrentPatientStatus(status);

        // Сохраняем в базу данных
        return appointmentRepository.save(appointment);
    }

    public List<DoctorAppiontmentResponseDto> getAppointmentsForDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    /**
     * Обновляет статус записи к врачу.
     * Доступно только врачам.
     *
     * @param appointmentId ID записи
     * @param newStatus новый статус
     * @return обновлённая информация о записи
     */
    public DoctorAppiontmentResponseDto updateAppointmentStatus(Long appointmentId, AppointmentStatus newStatus) {
        // Находим запись по ID
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment with id " + appointmentId + " not found"));

        // Получаем текущий статус пациента
        CurrentPatientStatus currentStatus = appointment.getCurrentPatientStatus();

        // Обновляем статус
        currentStatus.setStatus(newStatus);

        // Сохраняем изменения
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Возвращаем обновлённую информацию
        return appointmentMapper.toDto(savedAppointment);
    }
}