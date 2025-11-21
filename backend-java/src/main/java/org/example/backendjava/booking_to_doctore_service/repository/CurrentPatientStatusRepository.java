package org.example.backendjava.booking_to_doctore_service.repository;

import org.example.backendjava.booking_to_doctore_service.model.entity.CurrentPatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с текущим статусом пациента.
 */
@Repository
public interface CurrentPatientStatusRepository extends JpaRepository<CurrentPatientStatus, Long> {
}