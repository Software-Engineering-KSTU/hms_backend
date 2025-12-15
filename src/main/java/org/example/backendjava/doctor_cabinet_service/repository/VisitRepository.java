package org.example.backendjava.doctor_cabinet_service.repository;

import org.example.backendjava.doctor_cabinet_service.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByDoctor_Id(Long doctorId);

    List<Visit> findAllByPatient_Id(Long patientId);
}
