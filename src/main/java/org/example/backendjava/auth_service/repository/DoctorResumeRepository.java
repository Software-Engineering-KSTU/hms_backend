package org.example.backendjava.auth_service.repository;

import org.example.backendjava.auth_service.model.entity.DoctorResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorResumeRepository extends JpaRepository<DoctorResume, Long> {

    Optional<DoctorResume> findByDoctorId(Long doctorId);
}


