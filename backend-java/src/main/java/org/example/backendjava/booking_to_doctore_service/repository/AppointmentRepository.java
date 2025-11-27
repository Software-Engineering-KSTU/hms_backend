package org.example.backendjava.booking_to_doctore_service.repository;

import org.example.backendjava.booking_to_doctore_service.model.entity.Appointment;
import org.example.backendjava.booking_to_doctore_service.model.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

    Optional<Appointment> findByPatientIdAndDoctorId(Long patientId, Long doctorId);

    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);
    /**
     * Находит все записи для конкретного врача с определенным статусом.
     *
     * @param doctorId ID врача
     * @param status статус записи
     * @return список записей
     */
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.currentPatientStatus.status = :status ORDER BY a.dateTime DESC")
    List<Appointment> findByDoctorIdAndStatus(@Param("doctorId") Long doctorId, @Param("status") AppointmentStatus status);
}